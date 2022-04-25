package com.github.twitter.kafka.model;

import lombok.Data;

@Data
public class User {

    private Long twitterID;
    private String username;
    private String name;
    private String created_at;
    private String tweet;
    private int retweetCount;
    private String location;
}
