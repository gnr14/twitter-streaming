package com.github.twitter.kafka.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private static final Long TWITTER_ID = 1234L;
    private static final String USERNAME = "testUsername";
    private static final String NAME = "testName";
    private static final String CREATED_AT = "Mon Apr 25 00:32:59 +0000 2022";
    private static final String TWEET = " Hello twitter";
    private static final int RETWEET_COUNT = 2;
    private static final String LOCATION = "US";

    @Test
    public void testUserData() {

        User user = new User();
        user.setTwitterID(TWITTER_ID);
        user.setUsername(USERNAME);
        user.setName(NAME);
        user.setCreated_at(CREATED_AT);
        user.setTweet(TWEET);
        user.setRetweetCount(RETWEET_COUNT);
        user.setLocation(LOCATION);

        assertEquals(TWITTER_ID, user.getTwitterID());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(NAME, user.getName());
        assertEquals(CREATED_AT, user.getCreated_at());
        assertEquals(TWEET, user.getTweet());
        assertEquals(RETWEET_COUNT, user.getRetweetCount());
        assertEquals(LOCATION, user.getLocation());
    }
}
