package com.sqli.chatUI.models;


public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String bearerHeader;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        ADD_DOMAIN,
        TYPING,
        QUESTION,
        RESPONSE;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBearerHeader() {
        return bearerHeader;
    }

    public void setBearerHeader(String bearerHeader) {
        this.bearerHeader = bearerHeader;
    }

    public ChatMessage(){}
    public ChatMessage(String message)
    {
        this.content = message;
        this.sender = "";
        this.type = MessageType.CHAT;
        this.bearerHeader = "";
    }
}
