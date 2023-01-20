package com.example.demo.services;

import com.example.demo.persistence.IcaoData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExampleService {

    private final WebClient client = WebClient.builder().baseUrl("https://airport-web.appspot.com/_ah/api/airportsapi/v1/airports").build();

    public Mono<IcaoData> getSingleAirport(String icaoCode) {
        return client.get().uri(String.format("/%s", icaoCode)).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(IcaoData.class);
    }

}
