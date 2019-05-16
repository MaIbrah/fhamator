package com.sqli.chatbot.naivebayes.util.dto;

public class NoExistKeywordRequest {
    private String searchQuery;
    private String clientKeyword;

    public NoExistKeywordRequest(String searchQuery, String clientKeyword) {
        this.searchQuery = searchQuery;
        this.clientKeyword = clientKeyword;
    }

    public NoExistKeywordRequest() {
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getClientKeyword() {
        return clientKeyword;
    }

    public void setClientKeyword(String clientKeyword) {
        this.clientKeyword = clientKeyword;
    }
}
