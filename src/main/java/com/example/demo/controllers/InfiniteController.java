package com.example.demo.controllers;

import com.example.demo.services.CallInfiniteService;
import com.example.demo.services.InfiniteService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class InfiniteController {

    /**
     * Media Type as Text Event Stream keeps asynchronous connection between publisher and subscriber
     * @param service The service to be called
     * @return ServerResponse each time a text event occurs in the service call
     */

    @Bean
    RouterFunction<ServerResponse> infiniteRoutes(InfiniteService service) {
        return route()
                .GET("/infinite", r -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(service.manyHello(), String.class))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> callInfiniteRoutes(CallInfiniteService service) {
        return route()
                .GET("/callinfinite", r -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(service.getInfiniteViaCall(), String.class))
                .build();
    }

}
