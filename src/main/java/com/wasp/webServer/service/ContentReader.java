package com.wasp.webServer.service;

import com.wasp.webServer.exception.NotFoundException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ContentReader {
    private String webAppPath;

    public ContentReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public BufferedReader readContent(String uri) throws NotFoundException {
        try {
            return new BufferedReader(new FileReader(webAppPath + uri));
        } catch (FileNotFoundException e) {
            throw new NotFoundException("File not found by uri: " + webAppPath + uri, e);
        }
    }
}
