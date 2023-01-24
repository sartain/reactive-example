package com.example.demo.sundayleague.controllers;

import com.example.demo.sundayleague.services.SundayScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScoreController {

    @Autowired
    private SundayScoreService service;

    @GetMapping("/sundayscores")
    public ResponseEntity<List<String>> getScores() {
        return ResponseEntity.ok(service.getScores());
    }

    @PostMapping("/sundayscores")
    public ResponseEntity<String> putScores(@RequestBody String score) {
        service.addScore(score);
        return new ResponseEntity<String>(score, null, HttpStatus.CREATED);
    }

}
