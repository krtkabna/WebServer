package com.wasp.webServer;

import org.junit.jupiter.api.Test;
import java.io.IOException;

public class WebServerTest {

    @Test
    void testServer() throws IOException {
        Server server = new Server(3000, "src/main/resources");
        server.start();
    }
}
