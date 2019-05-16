package com.sqli.chatbot.naivebayes.util.dto;

public class NoExistDomainRequest {
    private String searchQuery;
    private String clientDomain;

    public NoExistDomainRequest(String searchQuery, String clientDomain) {
        this.searchQuery = searchQuery;
        this.clientDomain = clientDomain;
    }

    public NoExistDomainRequest() {
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getClientDomain() {
        return clientDomain;
    }

    public void setClientDomain(String clientDomain) {
        this.clientDomain = clientDomain;
    }
}
