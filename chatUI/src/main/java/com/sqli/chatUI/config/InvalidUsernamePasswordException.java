package com.sqli.chatUI.config;

public class InvalidUsernamePasswordException extends RuntimeException{
    public InvalidUsernamePasswordException(String message) {
        super(message);
    }
}
