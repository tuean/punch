package com.tuean.file;

import com.tuean.entity.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {

    public static Config getConfig() { return config;}

    private static Config config;

    public static Config readProperties() {
        Properties prop = new Properties();
        String fileName = "application.properties";

        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            // Load properties file
            prop.load(input);

            String serverPort = prop.getProperty("server.port");
            Integer port = Integer.parseInt(serverPort == null ? "8888" : serverPort);
            String markdownPath = prop.getProperty("markdown.path");
            String author = prop.getProperty("author");
            String source = prop.getProperty("file.proxy.location");
            config = new Config(port, markdownPath, author, source);
            return config;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
