package com.github.twitter.kafka.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class KafkaConfigTest {

    @Test
    public void validateKafkaConfig() {

        KafkaConfig config = new KafkaConfig();
        KafkaProducer<String, String> kafkaProducer = config.createKafkaProducer();

        assertNotNull(kafkaProducer.toString());

    }

}
