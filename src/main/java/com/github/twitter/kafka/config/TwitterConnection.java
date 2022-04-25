package com.github.twitter.kafka.config;

import com.github.twitter.kafka.constants.Twitter;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Twitter connection class
 */
public class TwitterConnection {

    public Client createTwitterClient(BlockingQueue<String> msgQueue) {
        List<String> trackTerms = Lists.newArrayList("#JustinBieber");
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        hosebirdEndpoint.trackTerms(trackTerms);
        Authentication hosebirdAuth = new OAuth1(Twitter.CONSUMER_KEYS, Twitter.CONSUMER_SECRETS,
                Twitter.TOKEN, Twitter.SECRET);
        ClientBuilder builder = new ClientBuilder()
                .name(Twitter.CLIENT_NAME)
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));
        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }
}
