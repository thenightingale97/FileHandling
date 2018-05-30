package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.managed.ScheduleFileDirectoryManaged;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Launcher extends Application<FileHandlerConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(FileHandlerConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule());
        ScheduleFileDirectory trackerFileDirectory = guice.getInstance(ScheduleFileDirectory.class);
        ScheduleFileDirectoryManaged managed = new ScheduleFileDirectoryManaged(trackerFileDirectory);
        environment.lifecycle().manage(managed);
        /*FileHandlerApplication application = guice.getInstance(FileHandlerApplication.class);*/
        trackerFileDirectory.goThroughToCheckFile();
    }
}
