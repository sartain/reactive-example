package com.example.demo.premierleague.pojo;

public class Score {

    public String getScoreString() {
        return scoreString;
    }

    public void setScoreString(String scoreString) {
        this.scoreString = scoreString;
    }

    private String scoreString;

    public Score() {}

    public Score(String s) { this.scoreString = s;}

}
