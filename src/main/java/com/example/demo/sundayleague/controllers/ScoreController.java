package com.example.demo.sundayleague.controllers;

import com.example.demo.sundayleague.services.SundayScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    // Highlight how text event stream can be used (blocking)

    @GetMapping(value = "/sundayscores/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<List<String>> getScoresList() {
        return ResponseEntity.ok(service.getScores());
    }

    @PostMapping("/sundayscores")
    public ResponseEntity<String> putScores(@RequestBody String score) {
        service.addScore(score);
        return new ResponseEntity<String>(score, null, HttpStatus.CREATED);
    }

}
