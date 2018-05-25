package filemanager.launcher;

import filemanager.directorytracker.ScheduleFileDirectory;

import java.io.*;
import java.util.Properties;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String fileName = "config.properties";
        ClassLoader classLoader = Launcher.class.getClassLoader();
        Properties properties = new Properties();

        try (InputStream resourceStream = classLoader.getResourceAsStream(fileName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScheduleFileDirectory scheduleFileDirectory = new ScheduleFileDirectory(properties.getProperty("rootFolder"),
                properties.getProperty("environment"),
                properties.getProperty("outputPath"));
        scheduleFileDirectory.goThroughToCheckFile();


       /* WatchFileDirectory watchFileDirectory = new WatchFileDirectory(properties.getProperty("rootFolder"),
                properties.getProperty("environment"),
                properties.getProperty("outputPath"),
                properties.getProperty("fileNamePattern"));
        watchFileDirectory.goThroughToCheckFile();*/

    }

}