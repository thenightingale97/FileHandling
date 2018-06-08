package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.configuration.ApplicationConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.healthchecks.InternetConnectionHealthCheck;
import filemanager.model.Command;
import filemanager.resource.ClientResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Launcher extends Application<ApplicationConfiguration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule(configuration));
        ScheduleFileDirectory trackerFileDirectory = guice.getInstance(ScheduleFileDirectory.class);
        environment.jersey().register(guice.getInstance(ClientResource.class));
        environment.lifecycle().scheduledExecutorService("scheduledTracker")
                .build()
                .scheduleWithFixedDelay(() ->
                        trackerFileDirectory.goThroughToCheckFile(new Command().setDate(LocalDateTime.now())), 0, Long.parseLong(configuration.getEnvironmentConfig().getTimeInterval()), TimeUnit.MINUTES);
        environment.healthChecks().register("Internet connection check", guice.getInstance(InternetConnectionHealthCheck.class));
        environment.healthChecks().runHealthChecks();
    }
}
