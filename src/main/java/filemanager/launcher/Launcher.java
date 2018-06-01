package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.resource.ClientResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.concurrent.TimeUnit;

public class Launcher extends Application<FileHandlerConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(FileHandlerConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule(configuration));
        ScheduleFileDirectory trackerFileDirectory = guice.getInstance(ScheduleFileDirectory.class);
        environment.jersey().register(new ClientResource(trackerFileDirectory));
        environment.lifecycle().scheduledExecutorService("scheduledTracker")
                .build()
                .schedule((Runnable) trackerFileDirectory::goThroughToCheckFile, Long.parseLong(configuration.getTimeInterval()), TimeUnit.MINUTES);
    }
}
