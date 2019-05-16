package com.sqli.chatUI.models;

import java.util.List;

public class SearchRequest {

    private String domain;
    private List<String> keyword;

    public SearchRequest() {

    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }
}
