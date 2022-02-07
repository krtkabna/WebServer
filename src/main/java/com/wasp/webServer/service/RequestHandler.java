package com.wasp.webServer.service;

import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.model.Request;
import com.wasp.webServer.model.Response;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        } catch (FileNotFoundException e) {
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            //throwing exception is a bad practice, will stop server. lod error instead
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            RESPONSE_WRITER.writeResponse(writer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
