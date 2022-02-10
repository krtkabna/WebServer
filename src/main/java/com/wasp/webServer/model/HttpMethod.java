package com.wasp.webServer.model;

import com.wasp.webServer.exception.MethodNotAllowedException;
import java.util.Arrays;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod getHttpMethodByName(String name) throws MethodNotAllowedException {
        return Arrays.stream(values())
            .filter(httpMethod -> name.equals(httpMethod.name()))
            .findFirst()
            .orElseThrow(MethodNotAllowedException::new);
    }
}
