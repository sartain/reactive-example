package com.example.demo.controllers;

import com.example.demo.persistence.IcaoData;
import com.example.demo.services.IcaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
public class IcaoController {

    @Bean
    RouterFunction<ServerResponse> exampleRoutes(IcaoService exampleService) {
        return route()
                .GET("/mono", r -> ServerResponse.ok().body(Mono.just("Hello"), String.class))
                .GET("/plane", r -> ServerResponse.ok().body(exampleService.getSingleAirport("EDLW"), IcaoData.class))
                .GET("/airport/{airport}", r -> ServerResponse.ok().body(exampleService.getSingleAirport(r.pathVariable("airport")), IcaoData.class))
                .GET("/airport", r -> ServerResponse.ok().body(exampleService.getSingleAirport("EDLW"), IcaoData.class))
                .GET("/airports", r -> ServerResponse.ok().body(Flux.concat(Arrays.asList(exampleService.getSingleAirport("EDLW"),
                                exampleService.getSingleAirport("EDDE"))), IcaoData.class))
                .POST("/airport", r -> ServerResponse.ok().body(r.bodyToMono(IcaoData.class), IcaoData.class))
                .build();
    }

    /**
     * Can be used to test what happens with using mvc
     * @return ResponseEntity with ICAO data
     */

    @GetMapping("/airport/nonmono")
    ResponseEntity<IcaoData> singleAirportMvc() {
        RestTemplate rt = new RestTemplate();
        return rt.getForEntity("https://airport-web.appspot.com/_ah/api/airportsapi/v1/airports/EDDE", IcaoData.class);
    }

}
