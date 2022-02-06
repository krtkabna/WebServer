package com.wasp.webServer.service;

import com.wasp.webServer.exception.WebServerException;
import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Request;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RequestHandler {
    private BufferedReader reader;
    private BufferedWriter writer;
    private ContentReader contentReader;

    public RequestHandler(BufferedReader reader, BufferedWriter writer, ContentReader contentReader) {
        this.reader = reader;
        this.writer = writer;
        this.contentReader = contentReader;
    }

    public void handle() {
        RequestParser requestParser = new RequestParser();
        ResponseWriter responseWriter = new ResponseWriter();
        try {
            Request request = requestParser.parseRequest(reader);

            Response response = new Response();
            response.setContent(contentReader.readContent(request.getUri()));
            response.setHttpStatus(HttpStatus.OK);

            responseWriter.writeResponse(writer, response);
        } catch (FileNotFoundException e) {
            responseWriter.writeResponse(writer, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            System.out.println(e.getCause());
            throw new WebServerException(e.getMessage(), e);
        }
    }
}
