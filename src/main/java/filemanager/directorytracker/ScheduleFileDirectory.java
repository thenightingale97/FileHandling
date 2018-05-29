package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.service.ConverterFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    private ConverterFromJsonToXmlService converter;

    @Inject
    public ScheduleFileDirectory(ConverterFromJsonToXmlService converter) {
        this.converter = converter;
        init();
    }

    @Override
    public void goThroughToCheckFile() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> this.goThroughToCheckFile(LocalDateTime.now()), 0, timeInterval, TimeUnit.MINUTES);
    }

    @Override
    public void goThroughToCheckFile(LocalDateTime time) {
        try {
            Files.walk(Paths.get(rootFolder + environment + "/" +
                    time.getYear() + "/" +
                    time.getMonthValue() + "/" +
                    time.getDayOfMonth() + "/" +
                    time.getHour()), FileVisitOption.FOLLOW_LINKS)
                    .filter(file -> Files.isRegularFile(file) && matchPattern(file.toString()))
                    .forEach(path -> {
                        try {
                            converter.readJsonConvertToXmlAndWrite(path, outputPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(fileNamePattern);
    }

}