package com.example.demo.premierleague.services;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class MvcScoreService {

    Properties consumerProperties;
    KafkaConsumer<String, String> consumer;

    /**
     * Setup a Kafka Consumer
     */

    public MvcScoreService() {
        consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "score_group");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<String, String>(consumerProperties);
        consumer.subscribe(List.of("scores"));
    }

    /**
     * Get the kafka consumer to poll for any score updates
     * Map the results into an array list to return
     * @return List of scores
     */

    public List<String> getScores() {
        List<String> scores = new ArrayList<>();
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        for(ConsumerRecord<String, String> record : records) {
            scores.add(record.value());
        }
        return scores;
    }

}
