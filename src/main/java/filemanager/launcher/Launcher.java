package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.concurrent.TimeUnit;

public class Launcher extends Application<FileHandlerConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(FileHandlerConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule());
        ScheduleFileDirectory trackerFileDirectory = guice.getInstance(ScheduleFileDirectory.class);
        trackerFileDirectory.rootFolder = configuration.getRootFolder();
        trackerFileDirectory.environment = configuration.getEnvironment();
        trackerFileDirectory.outputPath = configuration.getOutputPath();
        trackerFileDirectory.fileNamePattern = configuration.getFileNamePattern();
        environment.lifecycle().scheduledExecutorService("scheduledTracker")
                .build()
                .schedule((Runnable) trackerFileDirectory::goThroughToCheckFile, Long.parseLong(configuration.getTimeInterval()), TimeUnit.MINUTES);
    }
}