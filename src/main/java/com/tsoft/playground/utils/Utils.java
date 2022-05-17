package com.tsoft.playground.utils;

import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    /**
     * Reads a file from resources
     *
     * @param filename
     * @return
     */

    public String readFileFromResources(String filename) {
        try {
            try (
                            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            Stream<String> lines = bufferedReader.lines();
            ) {
                return lines.collect(Collectors.joining("\n"));
            }
            // File file = ResourceUtils.getFile("classpath:" + filename);
            // return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
