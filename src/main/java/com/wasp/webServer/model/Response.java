package com.wasp.webServer.model;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Map;

public class Response {
    private BufferedReader content;
    private HttpStatus httpStatus;
    private Map<String, String[]> headers;

    public BufferedReader getContent() {
        return content;
    }

    public void setContent(BufferedReader content) {
        this.content = content;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }
}
