package filemanager.directorytracker;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import filemanager.binder.FileServiceInjector;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;

import java.io.IOException;
import java.nio.file.*;

public class ScheduleFileDirectory implements TrackerFileDirectory {

    private String rootFolder;
    private String environment;
    private String outputPath;

    @Inject
    private ConverterFromJsonToXmlService converter;

    public ScheduleFileDirectory(String rootFolder, String environment, String outputPath) {
        this.rootFolder = rootFolder;
        this.environment = environment;
        this.outputPath = outputPath;
    }

    public ScheduleFileDirectory() {

    }

    @Override
    public void goThroughToCheckFile() {
        Injector injector = Guice.createInjector(new FileServiceInjector());
        converter = injector.getInstance(ConverterFromJsonToXmlServiceImpl.class);
        try {
            Files.walk(Paths.get(rootFolder + environment), FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile).forEach(path -> {

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


}
