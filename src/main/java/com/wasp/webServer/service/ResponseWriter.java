package com.wasp.webServer.service;

import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Response;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseWriter {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CRLF = "\r\n";

    public void writeResponse(BufferedWriter bufferedWriter, Response response) throws IOException {
        HttpStatus httpStatus = response.getHttpStatus();
        bufferedWriter.write(HTTP_VERSION + " " + httpStatus.toString());
        bufferedWriter.write(CRLF);
        if (response.getHeaders() != null) {
            writeHeaders(bufferedWriter, response.getHeaders());
            bufferedWriter.write(CRLF);
        }
        bufferedWriter.write(CRLF);
        //FIXME write files in portions in a loop
        String content = response.getContent().lines().collect(Collectors.joining());
        bufferedWriter.write(content);
        bufferedWriter.flush();
    }

    public void writeResponse(BufferedWriter bufferedWriter, HttpStatus httpStatus) {
        try {
            bufferedWriter.write(HTTP_VERSION + " " + httpStatus);
            bufferedWriter.write(CRLF);
            bufferedWriter.write(CRLF);
        } catch (IOException e) {
            e.printStackTrace();
            writeResponse(bufferedWriter, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
