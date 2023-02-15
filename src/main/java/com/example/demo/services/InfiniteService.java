package com.example.demo.services;

import com.example.demo.persistence.IcaoData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class InfiniteService {

    private final List<IcaoData> airportsWithNull = Arrays.asList(
            new IcaoData("EGGP", "Yesterday", "Liverpool John Lennon Airport", "URL"),
            new IcaoData("EGGL", "Today", "London Heathrow", "URL"),
            new IcaoData("EGGC", "Last week", "Manchester Airport", "URL"),
            null
    );

    private final List<IcaoData> airports = Arrays.asList(
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
                .delayElements(Duration.ofMillis(10));
    }

    public Flux<IcaoData> manyIcaoOnErrorResume() {
        Random rand = new Random();
        return Flux.fromStream(
                Stream.generate(() -> airportsWithNull.get(rand.nextInt(0, 4))))
                .onErrorResume(e -> manyIcaoOnErrorResume())
                .delayElements(Duration.ofMillis(10));
    }

    public Flux<IcaoData> manyIcaoOnErrorReturn() {
        Random rand = new Random();
        return Flux.fromStream(
                        Stream.generate(() -> airportsWithNull.get(rand.nextInt(0, 4))))
                .onErrorReturn(new IcaoData())
                .delayElements(Duration.ofMillis(10));
    }

    public Flux<IcaoData> manyIcaoBuffered() {
        Random rand = new Random();
        return Flux.fromStream(
                        Stream.generate(() -> airportsWithNull.get(rand.nextInt(0, 3))));
    }

    public Flux<String> setFlux() {
        return Flux.just("Hello","World")
                .concatWith(Flux.error(new RuntimeException("I have caused this error on purpose")))
                        .onErrorReturn("Default")
                .concatWith(Flux.just("Goodbye", "World"))
                .delayElements(Duration.ofSeconds(1));
    }

    public Mono<String> singleHello() {
        Mono<String> response = Mono.just(
                "Hello "
        ).map(e -> e + "World");
        response.subscribe();
        return response;
    }

}
