package com.sqli.chatbot.naivebayes.util.dto;

import java.io.Serializable;
import java.util.List;

public class SearchQueryResponse implements Serializable {
    private String domain;
    private List<String> keyword;

    public SearchQueryResponse(String domain, List<String> keyword) {
        this.domain = domain;
        this.keyword = keyword;
    }

    public SearchQueryResponse(){}

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
