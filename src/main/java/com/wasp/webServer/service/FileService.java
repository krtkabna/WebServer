package com.wasp.webServer.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.wasp.webServer.service.Constants.NOT_FOUND;

public final class FileService {
    static boolean isReadable(File file) {
        return file.isFile();
    }

    public static String readFile(String path) throws IOException {
        File file = new File(path);
        if (isReadable(file)) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                return fileReader.lines().collect(Collectors.joining());
            }
        } else {
            return NOT_FOUND + path;
        }
    }

    public static BufferedImage readImage(String path) throws IOException {
        File file = new File(path);
        return isReadable(file) ? ImageIO.read(new File(path)) : null;
    }
}
