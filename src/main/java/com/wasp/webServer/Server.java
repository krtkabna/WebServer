package com.wasp.webServer;

import com.wasp.webServer.model.HttpStatus;
import com.wasp.webServer.service.ContentReader;
import com.wasp.webServer.service.RequestHandler;
import com.wasp.webServer.service.ResponseWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private String webAppPath;
    private ContentReader contentReader;

    public Server(int port, String webAppPath) {
        this.port = port;
        this.webAppPath = webAppPath;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public void start() throws IOException {
        contentReader = new ContentReader(webAppPath);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Started server");
            int count = 0;
            while (!serverSocket.isClosed()) {
                count++;
                System.out.println("Started client #" + count);
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     ResponseWriter responseWriter = new ResponseWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))) {
                    RequestHandler requestHandler = new RequestHandler(bufferedReader, responseWriter, contentReader);
                    try {
                        requestHandler.handle();
                    } catch (Exception e) {
                        responseWriter.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Finished client #" + count);
            }
            System.out.println("Finished server");
        }
    }
}
