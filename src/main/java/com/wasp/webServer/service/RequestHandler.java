package com.wasp.webServer.service;

import com.wasp.webServer.exception.BadRequestException;
import com.wasp.webServer.exception.InternalServerErrorException;
import com.wasp.webServer.exception.MethodNotAllowedException;
import com.wasp.webServer.exception.NotFoundException;
import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Request;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RequestHandler {
    private static final RequestParser REQUEST_PARSER = new RequestParser();
    private static final ResponseWriter RESPONSE_WRITER = new ResponseWriter();
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final ContentReader contentReader;

    public RequestHandler(BufferedReader reader, BufferedWriter writer, ContentReader contentReader) {
        this.reader = reader;
        this.writer = writer;
        this.contentReader = contentReader;
    }

    public void handle() {
        try {
            Request request = REQUEST_PARSER.parseRequest(reader);

            Response response = new Response();
            response.setContent(contentReader.readContent(request.getUri()));
            response.setHttpStatus(HttpStatus.OK);

            RESPONSE_WRITER.writeResponse(writer, response);
        } catch (BadRequestException e) {
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (InternalServerErrorException e) {
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
