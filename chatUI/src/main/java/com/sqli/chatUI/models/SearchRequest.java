package com.sqli.chatUI.models;

import java.util.List;

public class SearchRequest {

//    private String domain;
//    private List<String> keyword;
//
//    public SearchRequest() {
//
//    }
//
//    public String getDomain() {
//        return domain;
//    }
//
//    public void setDomain(String domain) {
//        this.domain = domain;
//    }
//
//    public List<String> getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(List<String> keyword) {
//        this.keyword = keyword;
//    }

    private String query;
    private Entity bestDomainFound;
    private List<Entity> possibleDomains;
    private List<Entity> possibleKeywords;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Entity getBestDomainFound() {
        return bestDomainFound;
    }

    public void setBestDomainFound(Entity bestDomainFound) {
        this.bestDomainFound = bestDomainFound;
    }

    public List<Entity> getPossibleDomains() {
        return possibleDomains;
    }

    public void setPossibleDomains(List<Entity> possibleDomains) {
        this.possibleDomains = possibleDomains;
    }

    public List<Entity> getPossibleKeywords() {
        return possibleKeywords;
    }

    public void setPossibleKeywords(List<Entity> possibleKeywords) {
        this.possibleKeywords = possibleKeywords;
    }

}
