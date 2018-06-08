package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.healthchecks.InternetConnectionHealthCheck;
import filemanager.model.Command;
import filemanager.resource.ClientResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Launcher extends Application<FileHandlerConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(FileHandlerConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule(configuration));
        ScheduleFileDirectory trackerFileDirectory = guice.getInstance(ScheduleFileDirectory.class);
        environment.jersey().register(guice.getInstance(ClientResource.class));
        environment.lifecycle().scheduledExecutorService("scheduledTracker")
                .build()
                .scheduleWithFixedDelay(() -> {
                    Command command = new Command();
                    command.setDate(LocalDateTime.now());
                    trackerFileDirectory.goThroughToCheckFile(command);
                }, 0, Long.parseLong(configuration.getTimeInterval()), TimeUnit.MINUTES);
        environment.healthChecks().register("Internet connection check", guice.getInstance(InternetConnectionHealthCheck.class));
        environment.healthChecks().runHealthChecks();
    }
}
