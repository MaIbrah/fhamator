'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var searchingElement = document.querySelector('.searching');
var upResponseElement = document.querySelector('#upResponse');
var responseIndex = 0;

var stompClient = null;
var username = null;

var Searching = false;
var idAddButton = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/' + username, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify({sender: username, type: 'JOIN'})
    );
    window.location.href = "/chat";
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {


    var messageContent = messageInput.value.trim();
    console.log("=========================================================> "+messageContent);
    if (messageContent && stompClient) {
        console.log("=========================================================> username"+messageContent);
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        var messageElement = document.createElement('li');
        extracted(messageElement, chatMessage);
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(messageContent);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);
        console.log("=========================================================> username" + username);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;

        stompClient.send("/app/" + username + "/chat.sendReplyMessage", {}, JSON.stringify(chatMessage));
        console.log("=========================================================>/app/" + username + "chat.sendReplyMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';

        Searching = true;
        searchingElement.classList.remove('hidden');
    }


    event.preventDefault();
}

function extracted(messageElement, message) {
    messageElement.classList.add('chat-message');
    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);

    var avatarElement = document.createElement('i');
    if (message.sender === "BOT") {
        messageElement.classList.add('chat-message-bot');
        var avatarText = document.createElement("img");
        avatarText.src = "/images/robot-icon.png";
        avatarText.style="width : 42px";
    } else {
        messageElement.classList.add('chat-message-user');
        var avatarText = document.createTextNode(message.sender[0] + message.sender[1]);
    }

    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);


    // var massageTextElement = document.createElement('div');

}

function addDomainAndKeywordsRequest() {

    addDomain(document.getElementById("request").value, document.getElementById("domain").value);
    var keys =document.getElementById("keywords").value;
    if (keys!= "")
        addKeyword(keys);
    document.getElementById("myForm").style.display = "none";
    document.getElementById("domain").value = "";
    if (idAddButton != null) {
        document.getElementById(idAddButton).disabled = true;
        document.getElementById(idAddButton).style.color = "silver";
    }
    document.getElementById("message").disabled = false;
    sendUpResponseReply(event,'Thank you for contributing!');
}

function addDomain(searchQuery, clientDomain) {
    var url = "/api/naivesBayes/write/domain";
    var data = {
        "searchQuery": searchQuery,
        "clientDomain": clientDomain
    };

    $(document).ready(function () {
        $.ajax({
            url: url,
            type: "post",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json",
            complete: function (response) { // code_html contient le HTML renvoyé
            }
        });

    });
}

function addKeyword(clientKeywords) {
    var url = "/api/naivesBayes/write/keyword";
    var data = {
        "clientKeywords": clientKeywords
    };

    $(document).ready(function () {
        $.ajax({
            url: url,
            type: "post",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json",
            complete: function (response) { // code_html contient le HTML renvoyé

            }
        });

    });
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else if (message.type === 'ADD_DOMAIN') {
        var searchRequest = message.content;
        message.content = "I couldn't  understand you, can you please fill the form so I can help you next time";
        message.content += "<button id='" + (responseIndex) + "' class='fas fa-ellipsis-h fa-2x' style='color: #068dd0; background: none;' onclick='openForm(" + (responseIndex++) + ",\"" + searchRequest + "\")'></button>";
        extracted(messageElement, message);
    } else {
        extracted(messageElement, message);
    }

    Searching = false;
    searchingElement.classList.add('hidden');
    let textElement = document.createElement('p');
    let html = message.content;
    textElement.innerHTML = html;
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function openForm(id, searchRequest) {
    idAddButton = id;
    document.getElementById("request").value = searchRequest;
    document.getElementById("myForm").style.display = "block";
    document.getElementById("message").value = "";
    document.getElementById("message").disabled = true;
}

function closeForm() {
    document.getElementById("myForm").style.display = "none";
    document.getElementById("message").disabled = false;

}

function showResponse(index) {
    console.log("click!!");
    var disp = document.getElementById("reponses" + index).style.display;
    var className = document.getElementById("showResponseButton" + index).className;
    if (disp == "none") {
        disp = "unset";
        className = "fas fa-chevron-circle-up fa-3x chervon-up";
    } else {
        disp = "none";
        className = "fas fa-chevron-circle-down fa-3x chervon-up";
    }
    document.getElementById("reponses" + index).style.display = disp;
    document.getElementById("showResponseButton" + index).className = className;

}

function upThis(id, keyWords, responseInedx) {
    console.log("->" + id);
    console.log(keyWords);
    var url = "/REST/elasticsearch/keywords";
    var data = {
        "id": id,
        "keyWords": [
            keyWords
        ]
    };

    $(document).ready(function () {
        $.ajax({
            url: url,
            type: "put",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json"
        }).then(function (data, status, jqxhr) {

            // console.log(jqxhr);
        });
    });

    document.getElementById("upResponse" + responseInedx.valueOf()).style.pointerEvents = "none";
    document.getElementById("upResponse" + responseInedx.valueOf()).style.color = "#e0e0e0";
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function sendUpResponseReply(event,message) {

    if (stompClient) {
        var chatMessage = {
            sender: 'BOT',
            content: message ,
            type: 'CHAT'
        };

        var messageElement = document.createElement('li');
        extracted(messageElement, chatMessage);
        //stompClient.send("/app/"+username+"/chat.sendMessage", {}, JSON.stringify(chatMessage));
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(chatMessage.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;

        stompClient.send("/app/" + username + "/chat.sendReplyMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function showThis(idText, idShowMore) {
    $(idText).slideToggle();
    if ($(idShowMore).text() == "More") {
        $(idShowMore).text("Less")
    } else {
        $(idShowMore).text("More")
    }
}


messageForm.addEventListener('submit', sendMessage, true);