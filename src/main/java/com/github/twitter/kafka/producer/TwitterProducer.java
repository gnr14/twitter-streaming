package com.github.twitter.kafka.producer;

import com.github.twitter.kafka.config.KafkaConfig;
import com.github.twitter.kafka.config.TwitterConnection;
import com.github.twitter.kafka.constants.Kafka;
import com.github.twitter.kafka.constants.Twitter;
import com.github.twitter.kafka.model.User;
import com.github.twitter.kafka.util.Utils;
import com.oracle.javafx.jmx.json.JSONException;
import com.twitter.hbc.core.Client;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This class connect to hosebird client to get the twitter stream, fiter the tweets on justinbieber
 * and send them to kafka topic.
 */
@Data
public class TwitterProducer {

    Utils utils = new Utils();
    KafkaConfig kafkaConfig = new KafkaConfig();

    public static void main(String[] args) throws JSONException {
        new TwitterProducer().run();
    }

    public void run() throws JSONException {

        Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100);
        TwitterConnection connection = new TwitterConnection();
        Client client = connection.createTwitterClient(msgQueue);
        client.connect();
        KafkaProducer<String, String> producer = kafkaConfig.createKafkaProducer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stopping application and shutting Twitter Streaming Client");
            client.stop();
            logger.info("Closing Producer");
            producer.close();
        }));

        int tweetCount = 0;
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("Error while polling to queue : {}", e);
                client.stop();
            }

            if (msg != null && utils.checkIfKeywordMatch(msg)) {
                tweetCount = extractAndSendtoKafka(msg, producer, tweetCount, logger);
            }
        }
        logger.info("Number of total tweets consumed: {} ", tweetCount);
        logger.info("Number of unique tweets consumed: {} " , tweetCount);
    }

    private int extractAndSendtoKafka(String msg, KafkaProducer<String, String> producer, int tweetCount,
            Logger logger) {
        try {
            User user = extractFieldsFromJson(msg);
            tweetCount +=1;
            producer.send(
                    new ProducerRecord<>(Kafka.TOPIC, null, utils.convertDataToJson(user)),
                    (recordMetadata, e) -> {
                        if (e != null) {
                            logger.error("Unable to Produce the Message : {}", e);
                        } else {
                            logger.info(
                                    "Topic : {} Partition : {} Offset : {} TimeStamp : {}",
                                    recordMetadata.topic(), recordMetadata.partition(),
                                    recordMetadata.offset(), recordMetadata.timestamp());
                        }
                    });
        } catch (Exception e) {
            logger.error("Exception while processing the message to kafka : {}", e);
        }
        return tweetCount;
    }

    private User extractFieldsFromJson(String msg) {

        User user = new User();
        JSONObject object = new JSONObject(msg);
        user.setCreated_at(object.getString(Twitter.CREATED_AT));
        user.setTwitterID(object.getLong(Twitter.ID));
        user.setUsername(object.getJSONObject(Twitter.USER).getString(Twitter.SCREEN_NAME));
        user.setName(object.getJSONObject(Twitter.USER).getString(Twitter.NAME));

        user.setTweet(
                object.getString(Twitter.TEXT)
                        .replace("\n", " ")
                        .replace("\t", " "));
        user.setRetweetCount(object.getInt(Twitter.RETWEET_COUNT));
        user.setLocation(object.getJSONObject(Twitter.USER).getString(Twitter.LOCATION));
        return user;
    }

}
