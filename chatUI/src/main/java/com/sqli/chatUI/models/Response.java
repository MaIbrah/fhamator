package com.sqli.chatUI.models;

import java.util.List;

public class Response {
    private List<Information> information;
    private List<String> keyWords;


    public Response(List<Information> information, List<String> keyWords) {
        this.information = information;
        this.keyWords = keyWords;
    }

    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
