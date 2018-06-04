package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.model.Command;
import filemanager.service.ConverterFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    private ConverterFromJsonToXmlService converter;

    @Inject
    public ScheduleFileDirectory(ConverterFromJsonToXmlService converter) {
        this.converter = converter;
    }

    @Override
    public void goThroughToCheckFile() {
        this.goThroughToCheckFile(LocalDateTime.now());
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

    @Override
    public void goThroughToCheckFile(Command command) {
        String time = command.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        try {
            Files.walk(Paths.get(getRootFolder() + getEnvironment() + "/" +
                    dateTime.getYear() + "/" +
                    dateTime.getMonthValue() + "/" +
                    dateTime.getDayOfMonth() + "/" +
                    dateTime.getHour()), FileVisitOption.FOLLOW_LINKS)
                    .filter((file) -> Files.isRegularFile(file) && matchPattern(file.toString()))
                    .forEach(path -> {
                        try {
                            if (command.getClient().isEmpty()) {
                                converter.readJsonConvertToXmlAndWrite(path, getOutputPath());
                            } else {
                                converter.readJsonConvertToXmlAndWrite(path, getOutputPath(), command);
                            }
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

}
