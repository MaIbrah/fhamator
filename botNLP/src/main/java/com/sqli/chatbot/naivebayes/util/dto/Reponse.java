package com.sqli.chatbot.naivebayes.util.dto;

import java.util.List;

public class Reponse {
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

    public Reponse() {
    }

    public Reponse(String query, Entity bestDomainFound, List<Entity> possibleDomains, List<Entity> possibleKeywords) {
        this.query = query;
        this.bestDomainFound = bestDomainFound;
        this.possibleDomains = possibleDomains;
        this.possibleKeywords = possibleKeywords;
    }
}
