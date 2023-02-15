package com.example.demo.controllers;

import com.example.demo.persistence.IcaoData;
import com.example.demo.services.CallInfiniteService;
import com.example.demo.services.InfiniteService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class InfiniteController {

    /**
     * Media Type as Text Event Stream keeps asynchronous connection between publisher and subscriber
     * @param service The service to be called
     * @return ServerResponse each time a text event occurs in the service call
     */

    private InfiniteService infiniteService;

    public InfiniteController(InfiniteService service) {
        this.infiniteService = service;
    }

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

    @GetMapping(value = "/reservation/ndjson", produces = MediaType.APPLICATION_NDJSON_VALUE)
    ResponseEntity<Flux<IcaoData>> getReservationNdJson() {
        return ResponseEntity.ok(infiniteService.manyIcao());
    }

    @GetMapping(value = "/reservation/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<IcaoData>> getReservationEventStream() {
        return ResponseEntity.ok(infiniteService.manyIcao());
    }

    @GetMapping(value = "/flux/error/resume", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<IcaoData>> getErrorFlux() {
        return ResponseEntity.ok(infiniteService.manyIcaoOnErrorResume());
    }

    @GetMapping(value = "/flux/error/return", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<Flux<IcaoData>> getErrorFluxReturn() {
        return ResponseEntity.ok(infiniteService.manyIcaoOnErrorReturn());
    }

    @GetMapping(value = "/reservation/buffered")
    ResponseEntity<Flux<IcaoData>> getBufferedReservations() {
        return ResponseEntity.ok(infiniteService.manyIcaoBuffered().delayElements(Duration.ofSeconds(1)));
    }

}
