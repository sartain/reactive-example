package com.example.demo.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class InfiniteService {

    public Flux<String> manyHello() {
        return Flux.fromStream(
                Stream.generate(() -> "Hello World"))
                .delayElements(Duration.ofSeconds(1));
    }

}
