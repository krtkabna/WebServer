package com.wasp.webServer.service;

import com.wasp.webServer.exception.BadRequestException;
import com.wasp.webServer.exception.InternalServerErrorException;
import com.wasp.webServer.exception.MethodNotAllowedException;
import com.wasp.webServer.exception.NotFoundException;
import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Request;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;

public class RequestHandler {
    private static final RequestParser REQUEST_PARSER = new RequestParser();
    private final ResponseWriter responseWriter;
    private final BufferedReader reader;
    private final ContentReader contentReader;

    public RequestHandler(BufferedReader reader, ResponseWriter writer, ContentReader contentReader) {
        this.reader = reader;
        this.responseWriter = writer;
        this.contentReader = contentReader;
    }

    public void handle() {
        try {
            Request request = REQUEST_PARSER.parseRequest(reader);

            Response response = new Response();
            response.setContent(contentReader.readContent(request.getUri()));
            response.setHttpStatus(HttpStatus.OK);

            responseWriter.writeResponse(response);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
            responseWriter.writeResponse(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            responseWriter.writeResponse(HttpStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            System.out.println(e.getMessage());
            responseWriter.writeResponse(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (InternalServerErrorException e) {
            System.out.println(e.getMessage());
            responseWriter.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
