package com.github.twitter.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.twitter.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * util methods
 */
public class Utils {

    Logger logger = LoggerFactory.getLogger(Utils.class.getName());

    public boolean checkIfKeywordMatch(String key) {

        List<String> list = new ArrayList<>();
        list.add("music");
        list.add("Music");
        list.add("MUSIC");
        list.add("#music");
        list.add("#MUSIC");
        list.add("#Music");
        boolean match = list.stream().anyMatch(s -> key.contains(s));

        return match;
    }

    public String convertDataToJson(User user){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert object to json :{} with exception :{}", user.toString(),e);
        }
        return json;
    }
}
