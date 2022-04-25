package com.github.twitter.kafka.util;

import com.github.twitter.kafka.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    private static final Long TWITTER_ID = 1234L;
    private static final String USERNAME = "testUsername";
    private static final String NAME = "testName";
    private static final String CREATED_AT = "Mon Apr 25 00:32:59 +0000 2022";
    private static final String TWEET = " Hello twitter";
    private static final int RETWEET_COUNT = 2;
    private static final String LOCATION = "US";
    Utils util = new Utils();

    @Test
    public void testCheckIfKeywordMatch() {

        String value = "music";

        boolean actual = util.checkIfKeywordMatch(value);
        assertEquals(true, actual);

    }

    @Test
    public void testConvertDataToJson() {

        User user = new User();
        user.setTwitterID(TWITTER_ID);
        user.setUsername(USERNAME);
        user.setName(NAME);
        user.setCreated_at(CREATED_AT);
        user.setTweet(TWEET);
        user.setRetweetCount(RETWEET_COUNT);
        user.setLocation(LOCATION);
        String actual = util.convertDataToJson(user);
        String expected =
                "{\"twitterID\":1234,\"username\":\"testUsername\",\"name\":\"testName\",\"created_at\":\"Mon Apr 25 00:32:59 +0000 2022\",\"tweet\":\" Hello twitter\",\"retweetCount\":2,\"location\":\"US\"}";
        assertEquals(expected, actual);
    }
}
