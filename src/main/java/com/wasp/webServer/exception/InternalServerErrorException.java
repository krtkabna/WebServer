package com.wasp.webServer.exception;

public class InternalServerErrorException extends Exception {
    public InternalServerErrorException(String message, Exception cause) {
        super(message, cause);
    }
}
