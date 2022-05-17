package com.tsoft.playground.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    /**
     * Reads a file from resources
     *
     * @param filename
     * @return
     */

    public static String readFileFromResources(String filename) {
        try {
            File file = ResourceUtils.getFile("classpath:" + filename);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
