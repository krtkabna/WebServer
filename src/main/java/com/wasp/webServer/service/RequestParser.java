package com.wasp.webServer.service;

import com.wasp.webServer.model.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.webServer.model.HttpMethod.getHttpMethodByName;

public final class RequestParser {

    public Request parseRequest(BufferedReader reader) throws IOException {
        Request request = new Request();
        injectUriAndHttpMethod(reader, request);
        injectHeaders(reader, request);
        return request;
    }

    private void injectUriAndHttpMethod(BufferedReader reader, Request request) throws IOException {
        String[] split = reader.readLine().split("\\s");
        request.setHttpMethod(getHttpMethodByName(split[0]));
        request.setUri(split[1]);
    }

    private void injectHeaders(BufferedReader reader, Request request) throws IOException {
        String line;
        Map<String, String[]> headers = new HashMap<>();
        while (!(line = reader.readLine()).isEmpty()) {
            String[] header = line.split(": ");
            String[] options = header[1].split(", ");
            headers.put(header[0], options);
        }
        request.setHeaders(headers);
    }

}
