package com.tuean.file;

import com.tuean.config.Environment;
import com.tuean.entity.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {

    public static Config getConfig() { return config;}

    private static Config config;



    public static Config readProperties() {
        String fileName = "application.properties";

        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            // Load properties file
            Environment.properties.load(input);

            String serverPort = Environment.properties.getProperty("server.port");
            Integer port = Integer.parseInt(serverPort == null ? "8888" : serverPort);
            String markdownPath = Environment.properties.getProperty("markdown.path");
            String author = Environment.properties.getProperty("author");
            String source = Environment.properties.getProperty("file.proxy.location");
            config = new Config(port, markdownPath, author, source);
            return config;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
