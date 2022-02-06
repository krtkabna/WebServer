package com.wasp.webServer.service;

import com.wasp.webServer.exception.WebServerException;
import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseWriter {

    public void writeResponse(BufferedWriter bufferedWriter, Response response) throws IOException {
        HttpStatus httpStatus = response.getHttpStatus();
        bufferedWriter.write(httpStatus.toString());
        bufferedWriter.newLine();
        if (response.getHeaders() != null) {
            writeHeaders(bufferedWriter, response.getHeaders());
            bufferedWriter.newLine();
        }
        bufferedWriter.newLine();
        String content = response.getContent().lines().collect(Collectors.joining());
        bufferedWriter.write(content);
        bufferedWriter.flush();
    }

    public void writeResponse(BufferedWriter bufferedWriter, HttpStatus httpStatus) {
        try {
            bufferedWriter.write(httpStatus.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new WebServerException(e.getMessage(), e);
        }
    }

    //todo remove later
    public static void writeResponse(BufferedWriter bufferedWriter, Response response, HttpStatus httpStatus) throws IOException {
        System.out.println("Writing response");
        bufferedWriter.write(httpStatus.getCode() + " " + httpStatus.getMessage());
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        BufferedReader bufferedReader = response.getContent();
        bufferedWriter.write(bufferedReader.lines().collect(Collectors.joining()));
    }

    private static void writeHeaders(BufferedWriter bufferedWriter, Map<String, String[]> headers) throws IOException {
        Iterator<Map.Entry<String, String[]>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            bufferedWriter.write(entry.getKey() + ": " +
                Arrays.stream(entry.getValue()).collect(Collectors.joining(", ")));
            bufferedWriter.newLine();
        }
    }
}
