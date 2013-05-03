package com.cecere.springdemo.domain;

public class Tally {
    private String name;
    private Float averagePopularVote;
    private Float adamVote;
    private Float bobVote;
    private Float finalScore;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getAveragePopularVote() {
        return averagePopularVote;
    }
    public void setAveragePopularVote(Float averagePopularVote) {
        this.averagePopularVote = averagePopularVote;
    }
    public Float getAdamVote() {
        return adamVote;
    }
    public void setAdamVote(Float adamVote) {
        this.adamVote = adamVote;
    }
    public Float getBobVote() {
        return bobVote;
    }
    public void setBobVote(Float bobVote) {
        this.bobVote = bobVote;
    }
    public Float getFinalScore() {
        return finalScore;
    }
    public void setFinalScore(Float finalScore) {
        this.finalScore = finalScore;
    }
}
