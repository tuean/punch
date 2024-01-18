package com.tuean.file;

import com.tuean.config.Environment;
import com.tuean.entity.Config;
import com.tuean.helper.context.Ctx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Ctx
public class PropertiesFileReader {

    public static Config getConfig() { return config;}

    private static Config config;



    public static Config readProperties() {
        String fileName = "application.properties";

        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            // Load properties file
            Environment.init(input);

            String serverPort = Environment.getProperty("server.port");
            Integer port = Integer.parseInt(serverPort == null ? "8888" : serverPort);
            String markdownPath = Environment.getProperty("markdown.path");
            String author = Environment.getProperty("author");
            String source = Environment.getProperty("file.proxy.location");
            config = new Config(port, markdownPath, author, source);
            return config;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void initConfig() throws IOException {
        String fileName = "application.properties";

        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            Environment.init(input);
        }
    }

}
