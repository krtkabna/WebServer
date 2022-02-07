package com.wasp.webServer.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HttpMethod getHttpMethodByName(String name) {
        return Arrays.stream(values())
            .filter(httpMethod -> httpMethod.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("No HTTP method found by name: " + name));
    }
}
