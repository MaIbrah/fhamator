package com.sqli.chatUI.models;

public class Entity {
    private String intent;
    private double score;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Entity() {
    }
}