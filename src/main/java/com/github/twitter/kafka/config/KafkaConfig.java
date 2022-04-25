package com.github.twitter.kafka.config;

import com.github.twitter.kafka.constants.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Kafka configuration for producer
 */
public class KafkaConfig {

    public KafkaProducer<String, String> createKafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Kafka.BOOTSTRAPSERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, Kafka.ACKS_CONFIG);
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Kafka.RETRIES_CONFIG);
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, Kafka.MAX_IN_FLIGHT_CONN);
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, Kafka.COMPRESSION_TYPE);
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, Kafka.LINGER_CONFIG);
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Kafka.BATCH_SIZE);

        return new KafkaProducer<String, String>(properties);
    }

}
