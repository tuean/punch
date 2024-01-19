package com.tuean.file;

import com.tuean.annotation.InitMethod;
import com.tuean.config.Environment;
import com.tuean.entity.Config;
import com.tuean.helper.context.Ctx;
import com.tuean.helper.context.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {

    private static Logger logger = LoggerFactory.getLogger(PropertiesFileReader.class);


//    public static Config readProperties() {
//        String fileName = "application.properties";
//
//        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
//            // Load properties file
//            Environment.init(input);
//
//            String serverPort = Environment.getProperty("server.port");
//            Integer port = Integer.parseInt(serverPort == null ? "8888" : serverPort);
//            String markdownPath = Environment.getProperty("markdown.path");
//            String author = Environment.getProperty("author");
//            String source = Environment.getProperty("file.proxy.location");
//            config = new Config(port, markdownPath, author, source);
//            return config;
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    private static Environment env;


    public static Environment initConfig() throws IOException {
        String fileName = "application.properties";

        try (InputStream input = PropertiesFileReader.class.getClassLoader().getResourceAsStream(fileName)) {
            env = new Environment();
            env.init(input);
            return env;
        } catch (Exception var) {
            logger.error("load {} error", fileName, var);
            throw new RuntimeException("load config error");
        }
    }

    public Environment getEnv() { return env; }

}
