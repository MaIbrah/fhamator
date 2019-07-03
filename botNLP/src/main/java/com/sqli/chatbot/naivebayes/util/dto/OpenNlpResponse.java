package com.sqli.chatbot.naivebayes.util.dto;

public class OpenNlpResponse {
    private String predicatedResponse;
    private double prob;

    public OpenNlpResponse(String predicatedResponse, double prob) {
        this.predicatedResponse = predicatedResponse;
        this.prob = prob;
    }

    public OpenNlpResponse() {
    }

    public String getPredicatedResponse() {
//        return predicatedResponse ;
        return prob > 0.7 ? predicatedResponse : "";
    }

    public void setPredicatedResponse(String predicatedResponse) {
        this.predicatedResponse = predicatedResponse;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }
}
