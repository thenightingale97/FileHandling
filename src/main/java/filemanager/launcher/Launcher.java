package filemanager.launcher;

import filemanager.controller.Application;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        String fileName = "config.properties";
        ClassLoader classLoader = Launcher.class.getClassLoader();
        Properties properties = new Properties();

        try (InputStream resourceStream = classLoader.getResourceAsStream(fileName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Application application = new Application(properties.getProperty("environment"), properties.getProperty("rootFolder"),
                properties.getProperty("outputPath"), properties.getProperty("fileNamePattern"));

        application.runProgram();
    }

}