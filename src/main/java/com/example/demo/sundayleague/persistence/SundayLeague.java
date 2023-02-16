package com.example.demo.sundayleague.persistence;

import java.util.Arrays;
import java.util.List;

public class SundayLeague {

    List<String> scores;

    //Dummy data

    public SundayLeague() {
        scores = Arrays.asList("Everton 1-0 Arsenal", "PSG 0-1 Bayern Munich");
    }

    public void addScore(String score) {
        scores.add(score);
    }

    public List<String> getScores() {
        return scores;
    }

}
