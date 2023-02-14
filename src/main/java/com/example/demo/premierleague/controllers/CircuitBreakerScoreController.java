package com.example.demo.premierleague.controllers;

import com.example.demo.premierleague.pojo.Score;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class CircuitBreakerScoreController {

    public CircuitBreakerScoreController() {
    }

    //Example of making another call to the API which communicates with Kafka
    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @GetMapping(value = "/kafka/double", produces = "text/event-stream")
    ResponseEntity<Flux<Score>> getKafkaScoresViaCall() {
        return ResponseEntity.ok(WebClient.builder().build().get()
                .uri("http://localhost:8080/kafka/webflux")
                .retrieve()
                .bodyToFlux(Score.class)
        );
    }

}
