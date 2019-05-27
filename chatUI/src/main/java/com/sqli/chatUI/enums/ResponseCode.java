package com.sqli.chatUI.enums;

public enum ResponseCode {

    NO_DOMAIN_FOUND("No domain found"),NO_KEYWORD_FOUND("No keywords found"),NO_DATA_FOUND("No data found") ;

    String value;

    ResponseCode(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
