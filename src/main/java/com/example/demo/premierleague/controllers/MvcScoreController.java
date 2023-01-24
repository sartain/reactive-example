package com.example.demo.premierleague.controllers;

import com.example.demo.premierleague.services.MvcScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class MvcScoreController {

    @Autowired
    private MvcScoreService service;

    //Attempting mvc would lead to failure for attempting to block

    @GetMapping(value = "/kafka/mvc")
    ResponseEntity<List<String>> getKafkaScoresMvc() {
        return ResponseEntity.ok(service.getScores());
    }

}
