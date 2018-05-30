package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.TrackerFileDirectory;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Launcher extends Application<FileHandlerConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(FileHandlerConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule());/*
        FileHandlerApplication application = guice.getInstance(FileHandlerApplication.class);*/
        TrackerFileDirectory trackerFileDirectory = guice.getInstance(TrackerFileDirectory.class);
        trackerFileDirectory.goThroughToCheckFile();
    }
}
