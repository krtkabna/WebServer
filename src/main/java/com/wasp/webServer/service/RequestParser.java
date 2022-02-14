package com.wasp.webServer.service;

import com.wasp.webServer.exception.BadRequestException;
import com.wasp.webServer.exception.InternalServerErrorException;
import com.wasp.webServer.exception.MethodNotAllowedException;
import com.wasp.webServer.model.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.webServer.model.HttpMethod.getHttpMethodByName;

public final class RequestParser {

    public Request parseRequest(BufferedReader reader) throws MethodNotAllowedException,
                                                              InternalServerErrorException,
                                                              BadRequestException {
        try {
            Request request = new Request();
            injectUriAndHttpMethod(reader, request);
            injectHeaders(reader, request);
            return request;
        } catch (IOException e) {
            //todo fix Internal Server Error handling
            throw new InternalServerErrorException("An I/O exception occurred", e);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new BadRequestException("Could not parse request", e);
        }
    }

    private void injectUriAndHttpMethod(BufferedReader reader, Request request) throws IOException, MethodNotAllowedException {
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
