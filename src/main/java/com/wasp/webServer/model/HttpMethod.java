package com.wasp.webServer.model;

import com.wasp.webServer.exception.MethodNotAllowedException;
import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HttpMethod getHttpMethodByName(String name) throws MethodNotAllowedException {
        return Arrays.stream(values())
            .filter(httpMethod -> httpMethod.name.equals(name))
            .findFirst()
            .orElseThrow(MethodNotAllowedException::new);
    }
}
