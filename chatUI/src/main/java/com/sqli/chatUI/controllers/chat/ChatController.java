package com.sqli.chatUI.controllers.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sqli.chatUI.enums.ResponseCode;
import com.sqli.chatUI.models.ChatMessage;
import com.sqli.chatUI.services.RequestDispatcher;

@RestController
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private RequestDispatcher requestDispatcher;

    @MessageMapping("/{user}/chat.sendMessage")
    @SendTo("/topic/{user}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @PathVariable String user) throws InterruptedException {
        return chatMessage;
    }


    @MessageMapping("/{user}/chat.sendReplyMessage")
    @SendTo("/queue/{user}")
    public ChatMessage sendReplay(@Payload ChatMessage chatMessage, @PathVariable String user) throws InterruptedException {
        Thread.sleep(Long.parseLong("100"));
        ChatMessage message = new ChatMessage();
        String retournedMessage =returnMessage(chatMessage.getContent());

        if (ResponseCode.NO_DOMAIN_FOUND.getValue().equalsIgnoreCase(retournedMessage)){
            message.setContent(chatMessage.getContent());
            message.setType(ChatMessage.MessageType.ADD_DOMAIN);
        }
        else if(ResponseCode.NO_DATA_FOUND.getValue().equalsIgnoreCase(retournedMessage)) {
            message.setContent(ResponseCode.NO_DATA_FOUND.getValue());
            message.setType(ChatMessage.MessageType.CHAT);
        }
        else{
            message.setContent(retournedMessage);
            message.setType(ChatMessage.MessageType.CHAT);
        }
        message.setSender("BOT");
        messagingTemplate.convertAndSend("/topic/"+chatMessage.getSender(),message);
        return chatMessage;
    }



  /*  @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor,
                               HttpSession httpSession) {
        if( headerAccessor.getSessionAttributes().get("username").equals(chatMessage.getSender()))
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }*/


    private String returnMessage(String text)
    {
        return requestDispatcher.requestDispatcher(text);
    }


}
