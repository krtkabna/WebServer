package com.wasp.webServer;

import com.wasp.webServer.service.RequestParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.wasp.webServer.service.FileService.readFile;
import static com.wasp.webServer.service.ResponseComposer.checkContentAndWriteResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    private int port;
    private String resourceHome;


    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Started");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    String resourcePath = getResourcePathAndPrintRequest(bufferedReader);
                    checkContentAndWriteResponse(bufferedWriter, readFile(resourcePath));
                    }
                    System.out.println("Finish");
                }
            }
        }



    private String getResourcePathAndPrintRequest(BufferedReader bufferedReader) throws IOException {
        String line, resourcePath = "";
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            if (RequestParser.isHeader(line)) {
                resourcePath = resourceHome + RequestParser.parseResourceName(line);
            }
            System.out.println(line);
        }
        return resourcePath;
    }
}
