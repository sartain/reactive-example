package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class ScoreService {

    @Autowired
    KafkaReceiver<String, String> kafkaReceiver;

    /**
     * Receive any data added to the topic
     * When calling receive, a consumer is created to consume
     * Acknowledge receipt
     * Map data to retrieve only the value
     * @return A flux of the values
     */

    public Flux<String> getScoresViaCall() {
        return kafkaReceiver.receive()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value);
    }

}
