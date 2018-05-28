package filemanager.directorytracker;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;

import java.io.IOException;
import java.nio.file.*;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    @Inject
    private ConverterFromJsonToXmlService converter;

    public ScheduleFileDirectory() {
        init();
    }

    @Override
    public void goThroughToCheckFile() {
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
    }

    public boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(fileNamePattern);
    }

}
