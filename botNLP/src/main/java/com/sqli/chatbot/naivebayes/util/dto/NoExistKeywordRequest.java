package com.sqli.chatbot.naivebayes.util.dto;

import java.util.List;

public class NoExistKeywordRequest {
    private List<String> clientKeywords;

    public NoExistKeywordRequest(List<String> clientKeywords) {
        this.clientKeywords = clientKeywords;
    }

    public NoExistKeywordRequest() {
    }

    public List<String> getClientKeywords() {
        return clientKeywords;
    }

    public void setClientKeywords(List<String> clientKeywords) {
        this.clientKeywords = clientKeywords;
    }
}
