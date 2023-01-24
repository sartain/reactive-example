package com.example.demo.controllers;

import com.example.demo.services.CallInfiniteService;
import com.example.demo.services.ScoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class ScoreController {

    @Bean
    RouterFunction<ServerResponse> getScoresViaKafka(ScoreService service) {
        return route()
                .GET("/kafka", r -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(service.getScoresViaCall(), String.class))
                .build();
    }

}
