package com.example.demo.premierleague.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.List;

@Service
public class ScoreService {

    //Two Kafka Receivers belonging to individual groups meaning they will both consume the same updates

    @Autowired
    KafkaReceiver<String, String> kafkaScoreReceiver;

    @Autowired
    KafkaReceiver<String, String> kafkaScoreReceiver2;

    /**
     * Receive any data added to the topic
     * When calling receive, a consumer is created to consume
     * Acknowledge receipt
     * Map data to retrieve only the value
     * @return A flux of the values
     */

    public Flux<String> getScoresViaCall() {
        return kafkaScoreReceiver.receive()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value);
    }

    public Flux<String> getModifiedScoresViaCall() {
        return kafkaScoreReceiver2.receive()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value)
                .map(r -> "GOAL UPDATE: " + r);
    }

}
