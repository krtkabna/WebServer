package com.wasp.webServer.model;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

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
            .orElseThrow(NoSuchElementException::new);
    }
}
