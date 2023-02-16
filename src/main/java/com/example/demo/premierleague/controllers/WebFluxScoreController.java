package com.example.demo.premierleague.controllers;

import com.example.demo.premierleague.pojo.Score;
import com.example.demo.premierleague.services.ScoreService;
import com.example.demo.services.InfiniteService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class WebFluxScoreController {

    private ScoreService scoreService;

    public WebFluxScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Bean
    RouterFunction<ServerResponse> getScoresViaKafka() {
        return route()
                .GET("/kafka", r -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(scoreService.getScoresViaCall(), String.class))
                .build();
    }

    //Equivalent to above

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @GetMapping(value = "/kafka/webflux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    ResponseEntity<Flux<Score>> getKafkaScores() {
        return ResponseEntity.ok(scoreService.getScoresViaCall().map(Score::new));
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
    @GetMapping(value = "/auth/kafka/webflux", produces = "application/json")
    ResponseEntity<Flux<String>> getKafkaScoresAuthOnly() {
        return ResponseEntity.ok(scoreService.getScoresViaAuthCall());
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

    //Example of an MVC calling the reactive system to get score updates

    @GetMapping(value = "/kafka/mvc", produces = "text/event-stream")
    ResponseEntity<List<String>> getKafkaScoresMvc() {
        RestTemplate template = new RestTemplate();
        return template.exchange("http://localhost:8080/kafka/webflux/callme",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
    }

    // For example purposes only
    // Highlight how unnecessary webflux is if pipeline is blocking

    @GetMapping(value = "/sundayscores/here", produces = "text/event-stream")
    ResponseEntity<List<String>> getSundayScores() {
        RestTemplate template = new RestTemplate();
        return template.exchange("http://localhost:8080/sundayscores",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
    }

    @GetMapping(value = "/sundayscores/webflux")
    ResponseEntity<Flux<String>> getSundayScoresWebFlux() {
        return ResponseEntity.ok(WebClient.builder().build().get()
                .uri("http://localhost:8080/sundayscores/here")
                .retrieve()
                .bodyToFlux(String.class)
        );
    }

}
