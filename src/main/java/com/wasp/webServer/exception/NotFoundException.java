package com.wasp.webServer.exception;

public class NotFoundException extends Exception {
    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
