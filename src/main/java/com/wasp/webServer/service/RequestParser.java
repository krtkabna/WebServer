package com.wasp.webServer.service;

public final class RequestParser {
    private static final String GET = "GET ";

    public static boolean isHeader(String request) {
        return request.startsWith(GET);
    }

    public static String parseResourceName(String request) {
        String[] parts = request.split("\\s");
        return parts[1];
    }

}
