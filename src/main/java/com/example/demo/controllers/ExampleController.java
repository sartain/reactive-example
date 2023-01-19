package com.example.demo.controllers;

import com.example.demo.data.ICAOData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ExampleController {

    private final WebClient client = WebClient.builder().baseUrl("https://airport-web.appspot.com/_ah/api/airportsapi/v1/airports").build();

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
        return client.get().uri("/EDLW").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ICAOData.class);
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
    Flux<ICAOData> multipleAirports() {
        Flux<ICAOData> one = client.get().uri("/EDLW").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(ICAOData.class);
        Flux<ICAOData> two = client.get().uri("/EDDE").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(ICAOData.class);
        return one.concatWith(two);
    }

}
