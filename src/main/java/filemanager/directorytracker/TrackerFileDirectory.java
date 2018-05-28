package filemanager.directorytracker;

import filemanager.launcher.Launcher;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

public abstract class TrackerFileDirectory {

    String rootFolder;
    String environment;
    String outputPath;
    String fileNamePattern;
    Long timeInterval;

    abstract public void goThroughToCheckFile();

    abstract public void goThroughToCheckFile(LocalDateTime time);

    void init() {
        String fileName = "config.properties";
        ClassLoader classLoader = Launcher.class.getClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = classLoader.getResourceAsStream(fileName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootFolder = properties.getProperty("rootFolder");
        outputPath = properties.getProperty("outputPath");
        environment = properties.getProperty("environment");
        fileNamePattern = properties.getProperty("fileNamePattern");
        timeInterval = Long.valueOf(properties.getProperty("timeInterval"));
    }
}
