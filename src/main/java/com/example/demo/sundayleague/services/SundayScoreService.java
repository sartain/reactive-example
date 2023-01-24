package com.example.demo.sundayleague.services;

import com.example.demo.sundayleague.persistence.SundayLeague;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SundayScoreService {

    public SundayScoreService() {

    }

    SundayLeague sundayLeague = new SundayLeague();

    public List<String> getScores() {
        return sundayLeague.getScores();
    }

    public void addScore(String score) {
        sundayLeague.addScore(score);
    }

}
