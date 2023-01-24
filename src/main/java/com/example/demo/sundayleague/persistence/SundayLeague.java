package com.example.demo.sundayleague.persistence;

import java.util.ArrayList;
import java.util.List;

public class SundayLeague {

    List<String> scores;

    public SundayLeague() {
        scores = new ArrayList<>();
    }

    public void addScore(String score) {
        scores.add(score);
    }

    public List<String> getScores() {
        return scores;
    }

}
