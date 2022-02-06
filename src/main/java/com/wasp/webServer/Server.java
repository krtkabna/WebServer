package com.wasp.webServer;

import com.wasp.webServer.service.ContentReader;
import com.wasp.webServer.service.RequestHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
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
        this.contentReader = new ContentReader(webAppPath);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Started");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))
                {
                    RequestHandler requestHandler = new RequestHandler(bufferedReader, bufferedWriter, contentReader);
                    requestHandler.handle();
                }
                System.out.println("Finish");
            }
        }
    }
}
