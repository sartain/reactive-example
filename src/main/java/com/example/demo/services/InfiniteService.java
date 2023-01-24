package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class InfiniteService {

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

}
