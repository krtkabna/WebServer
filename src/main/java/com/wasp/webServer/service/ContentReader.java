package com.wasp.webServer.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ContentReader {
    private String webAppPath;

    public ContentReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public BufferedReader readContent(String uri) throws FileNotFoundException {
        return new BufferedReader(new FileReader(webAppPath + uri));
    }
}
