package com.example.demo.premierleague.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class CircuitBreakerScoreController {

    public CircuitBreakerScoreController() {
    }

    //Example of making another call to the API which communicates with Kafka

    @GetMapping(value = "/kafka/double", produces = "text/event-stream")
    ResponseEntity<Flux<String>> getKafkaScoresViaCall() {
        return ResponseEntity.ok(WebClient.builder().build().get()
                .uri("http://localhost:8080/kafka/webflux")
                .retrieve()
                .bodyToFlux(String.class)
        );
    }

}
