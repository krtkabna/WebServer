package com.wasp.webServer.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message, Exception cause) {
        super(message, cause);
    }
}
