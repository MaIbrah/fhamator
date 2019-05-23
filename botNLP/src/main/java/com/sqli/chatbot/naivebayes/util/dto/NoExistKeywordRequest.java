package com.sqli.chatbot.naivebayes.util.dto;

import java.util.List;

public class NoExistKeywordRequest {
    private String clientKeywords;

    public NoExistKeywordRequest(String clientKeywords) {
        this.clientKeywords = clientKeywords;
    }

    public NoExistKeywordRequest() {
    }

    public String getClientKeywords() {
        return clientKeywords;
    }

    public void setClientKeywords(String clientKeywords) {
        this.clientKeywords = clientKeywords;
    }
}
