package com.example.demo.premierleague.controllers;

import com.example.demo.premierleague.services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class WebFluxScoreController {

    @Autowired
    private ScoreService scoreService;

    @Bean
    RouterFunction<ServerResponse> getScoresViaKafka() {
        return route()
                .GET("/kafka", r -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(scoreService.getScoresViaCall(), String.class))
                .build();
    }

    //Equivalent to above

    @GetMapping(value = "/kafka/webflux", produces = "text/event-stream")
    ResponseEntity<Flux<String>> getKafkaScores() {
        return ResponseEntity.ok(scoreService.getScoresViaCall());
    }

    @GetMapping(value = "/kafka/webflux/callme", produces = "text/event-stream")
    Flux<String> getScoresCallingScoreService() {
        return scoreService.getScoresViaCall();
    }

    //Example of applying some logic to the response

    @GetMapping(value = "/kafka/webflux/teams", produces = "text/event-stream")
    ResponseEntity<Flux<String>> getKafkaTeams() {
        return ResponseEntity.ok(scoreService.getTeamNamesInCall());
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

    @GetMapping(value = "/kafka/mvc", produces = "text/event-stream")
    ResponseEntity<List<String>> getKafkaScoresMvc() {
        RestTemplate template = new RestTemplate();
        return template.exchange("http://localhost:8080/kafka/webflux/callme",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
    }

}
