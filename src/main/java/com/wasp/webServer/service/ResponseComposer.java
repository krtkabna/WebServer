package com.wasp.webServer.service;

import java.io.BufferedWriter;
import java.io.IOException;

import static com.wasp.webServer.service.Constants.*;

public class ResponseComposer {

    public static void writeResponse(BufferedWriter bufferedWriter, String response, String httpResponseCode) throws IOException {
        System.out.println(WRITING_RESPONSE);
        bufferedWriter.write(httpResponseCode);
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(response);
        bufferedWriter.flush();
    }


    public static void writeHttp200Response(BufferedWriter bufferedWriter, String response) throws IOException {
        writeResponse(bufferedWriter, response, HTTP_200_OK);
    }

    public static void writeHttp404Response(BufferedWriter bufferedWriter, String response) throws IOException {
        writeResponse(bufferedWriter, response, HTTP_404_NOT_FOUND);
    }

    public static void checkContentAndWriteResponse(BufferedWriter bufferedWriter, String content) throws IOException {
        if (content.startsWith(NOT_FOUND)) {
            ResponseComposer.writeHttp404Response(bufferedWriter, content);
        } else {
            ResponseComposer.writeHttp200Response(bufferedWriter, content);
        }
    }
}
