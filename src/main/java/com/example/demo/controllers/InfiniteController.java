package com.example.demo.controllers;

import com.example.demo.persistence.IcaoData;
import com.example.demo.services.ExampleService;
import com.example.demo.services.InfiniteService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class InfiniteController {

    @Bean
    RouterFunction<ServerResponse> infiniteRoutes(InfiniteService service) {
        return route()
                .GET("/infinite", r -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(service.manyHello(), String.class))
                .build();
    }

}
