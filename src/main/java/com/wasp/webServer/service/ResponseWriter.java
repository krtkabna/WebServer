package com.wasp.webServer.service;

import com.wasp.webServer.exception.InternalServerErrorException;
import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class ResponseWriter {
    private static final String RESPONSE_FORMAT = "HTTP/1.1 %s\r\n";
    private static final String HTML_FORMAT =
        "<!DOCTYPE html>" +
            "<html>" +
            "   <head><style> .status {text-align: center;padding: 50px;}</style></head>" +
            "   <body><h1 class=\"status\">%s</h1></body>" +
            "</html>";
    private static final String CRLF = "\r\n";
    private char[] buffer = new char[1024];

    public void writeResponse(BufferedWriter bufferedWriter, Response response) throws InternalServerErrorException {
        try {
            HttpStatus httpStatus = response.getHttpStatus();
            bufferedWriter.write(String.format(RESPONSE_FORMAT, httpStatus));
            if (response.getHeaders() != null) {
                writeHeaders(bufferedWriter, response.getHeaders());
            }
            bufferedWriter.write(CRLF);
            writeContent(bufferedWriter, response);
        } catch (IOException e) {
            throw new InternalServerErrorException();
        }
    }

    public void writeResponse(BufferedWriter bufferedWriter, HttpStatus httpStatus) {
        try {
            bufferedWriter.write(String.format(RESPONSE_FORMAT, httpStatus));
            bufferedWriter.write(CRLF);
            bufferedWriter.write(String.format(HTML_FORMAT, httpStatus));
        } catch (IOException e) {
            //FIXME no idea how to write response now
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void writeHeaders(BufferedWriter bufferedWriter, Map<String, String[]> headers) throws IOException {
        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            bufferedWriter.write(String.format("%s: %s%n", header.getKey(), Arrays.toString(header.getValue())));
        }
    }

    private void writeContent(BufferedWriter bufferedWriter, Response response) throws IOException {
        BufferedReader responseReader = response.getContent();
        while (responseReader.read(buffer) != -1) {
            bufferedWriter.write(buffer);
            bufferedWriter.write(CRLF);
        }
        responseReader.close();
    }
}
