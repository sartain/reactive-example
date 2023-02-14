package com.example.demo.services;

import com.example.demo.persistence.IcaoData;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class InfiniteService {

    private List<IcaoData> airports = Arrays.asList(
            new IcaoData("EGGP", "Yesterday", "Liverpool John Lennon Airport", "URL"),
            new IcaoData("EGGL", "Today", "London Heathrow", "URL"),
            new IcaoData("EGGC", "Last week", "Manchester Airport", "URL")
    );

    /**
     * Example of reactive streaming
     * Generates a stream of 'hello world' updating each second
     * @return Flux of 'hello world' propagated each second
     */

    public Flux<String> manyHello() {
        return Flux.fromStream(
                Stream.generate(() -> "Hello World"))
                .delayElements(Duration.ofSeconds(1));
    }

    public Flux<IcaoData> manyIcao() {
        Random rand = new Random();
        return Flux.fromStream(
                Stream.generate(() -> airports.get(rand.nextInt(0, 3))))
                .delayElements(Duration.ofSeconds(1));
    }

    public Mono<String> singleHello() {
        return Mono.just(
                "Hello "
        ).map(e -> e + "World");
    }

}
