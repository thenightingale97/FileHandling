package filemanager.directorytracker;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import filemanager.binder.FileServiceInjector;
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
