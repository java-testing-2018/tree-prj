package com.tree.springcloud.bl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestUtils {

    private TestUtils() {
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String json(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
