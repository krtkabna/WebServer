package com.wasp.webServer.exception;

import java.io.FileNotFoundException;

public class WebServerException extends RuntimeException {
    public WebServerException(String message) {
        super(message);
    }

    public WebServerException(String message, Exception e) {
        super(message, e);
    }
}
