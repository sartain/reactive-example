package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.stream.Collectors;

@Service
public class CallInfiniteService {

    private final WebClient client = WebClient.builder().baseUrl("http://localhost:8080").build();

    public Flux<String> getInfiniteViaCall() {
        return client.get()
                .uri("/infinite")
                .retrieve()
                .bodyToFlux(String.class)
                .map(e -> e + "!!!");
    }

}
