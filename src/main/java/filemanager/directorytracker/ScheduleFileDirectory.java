package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.service.ConverterFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    @Inject
    private ConverterFromJsonToXmlService converter;

    public ScheduleFileDirectory() {
        init();
    }

    @Override
    public void goThroughToCheckFile() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                Files.walk(Paths.get(rootFolder + environment), FileVisitOption.FOLLOW_LINKS)
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
        }, 0, timeInterval, TimeUnit.MINUTES);
    }

    public boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(fileNamePattern);
    }

}
