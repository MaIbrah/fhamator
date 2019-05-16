'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var upResponseElement = document.querySelector('#upResponse');
var responseIndex = 0;

var stompClient = null;
var username = null;

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
    )
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        var messageElement = document.createElement('li');
        extracted(messageElement, chatMessage);
        //stompClient.send("/app/"+username+"/chat.sendMessage", {}, JSON.stringify(chatMessage));

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(messageContent);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;

        stompClient.send("/app/" + username + "/chat.sendReplyMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function extracted(messageElement, message) {
    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.sender[0] + message.sender[1]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
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
    } else {
        extracted(messageElement, message);
    }

    let textElement = document.createElement('p');;
    let html = message.content;
    textElement.innerHTML = html;
        //textElement = document.createElement('p');
       // textElement.innerHTML += "<strong style='color: #ff4743'>" + message.content + "</strong>"
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function showResponse(index) {
    console.log("click!!")
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

            console.log(jqxhr);
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

function sendUpResponseReply(event) {

    if (stompClient) {
        var chatMessage = {
            sender: 'BOT',
            content: 'OKK',
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

function showThis(className){
    console.log("********makhdamach");
    $("#"+className).slideToggle();
}
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
