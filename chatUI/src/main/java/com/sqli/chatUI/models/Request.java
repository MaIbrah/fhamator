package com.sqli.chatUI.models;

public class Request {

    private String action;

    public Request(String action) {
        this.action = action;
    }

    public Request() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
