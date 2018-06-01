package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.service.ConverterFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    private ConverterFromJsonToXmlService converter;
    private LocalDateTime timeNow;

    @Inject
    public ScheduleFileDirectory(ConverterFromJsonToXmlService converter) {
        this.converter = converter;
        timeNow = LocalDateTime.now();
    }

    @Override
    public void goThroughToCheckFile() {
        this.goThroughToCheckFile(timeNow);
    }

    @Override
    public void goThroughToCheckFile(LocalDateTime time) {
        try {
            Files.walk(Paths.get(getRootFolder() + getEnvironment() + "/" +
                    time.getYear() + "/" +
                    time.getMonthValue() + "/" +
                    time.getDayOfMonth() + "/" +
                    time.getHour()), FileVisitOption.FOLLOW_LINKS)
                    .filter(file -> Files.isRegularFile(file) && matchPattern(file.toString()))
                    .forEach(path -> {
                        try {
                            converter.readJsonConvertToXmlAndWrite(path, getOutputPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(getFileNamePattern());
    }

    public LocalDateTime getTimeNow() {
        return timeNow;
    }

}