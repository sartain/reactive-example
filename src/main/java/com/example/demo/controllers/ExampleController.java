package com.example.demo.controllers;

import com.example.demo.persistence.ICAOData;
import com.example.demo.services.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class ExampleController {

    public ExampleController(ExampleService service) {
        this.service = service;
    }

    @Autowired
    private final ExampleService service;

    @Bean
    RouterFunction<ServerResponse> routes(ExampleService exampleService) {
        return route().GET("/plane", r -> ServerResponse.ok().body(exampleService.getSingleAirport("EDLW"), ICAOData.class))
                .build();
    }

    /*Equivalent to
    @GetMapping("/plane")
    Mono<ServerResponse> getPlane() {
        return ServerResponse.ok().body(service.getSingleAirport("EDLW"), ICAOData.class);
    }
    */

    /**
     * Simple mono object to test response
     * @return Mono<String> string
     */

    @GetMapping("/mono")
    Mono<String> mono() {
        return Mono.just("Hello");
    }

    /**
     * Retrieve airport information given a webclient API call to retrieve a mono
     * @return Mono of airport ICAO data
     */

    @GetMapping("/airport")
    public Mono<ICAOData> singleAirport() {
        return service.getSingleAirport("EDLW");
    }

    /**
     * Can be used to test what happens with using mvc
     * @return ResponseEntity with ICAO data
     */

    @GetMapping("/airport/nonmono")
    ResponseEntity<ICAOData> singleAirportMvc() {
        RestTemplate rt = new RestTemplate();
        return rt.getForEntity("https://airport-web.appspot.com/_ah/api/airportsapi/v1/airports/EDDE", ICAOData.class);
    }

    /**
     * Two separate API calls both retrieving a flux
     * Combine flux at the end to return an array of ICAOData
     * @return ICAOData flux
     */

    @GetMapping("/airports")
    List<Mono<ICAOData>> multipleAirports() {
        Mono<ICAOData> one = service.getSingleAirport("EDLW");
        Mono<ICAOData> two = service.getSingleAirport("EDDE");
        return Arrays.asList(one, two);
    }

}
