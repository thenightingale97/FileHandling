package filemanager.binder;

import com.codahale.metrics.Counter;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import filemanager.configuration.ApplicationConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.WatchFileDirectory;
import filemanager.healthchecks.InternetConnectionHealthCheck;
import filemanager.service.*;
import filemanager.service.impl.*;
import io.dropwizard.setup.Environment;

import static com.codahale.metrics.MetricRegistry.name;

public class FileServiceBinderModule extends AbstractModule {

    private ApplicationConfiguration configuration;

    private Environment environment;

    public FileServiceBinderModule(ApplicationConfiguration configuration,
                                   Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(InteractionGroupService.class).to(InteractionGroupServiceImpl.class);
        bind(JsonReadService.class).to(JsonReadServiceImpl.class);
        bind(XmlWriteService.class).to(XmlWriteServiceImpl.class);
        bind(FeedExporter.class);
        bind(JobWriterService.class).to(JobWriterServiceImpl.class);
        bind(StatsExportService.class).to(StatsExportServiceImpl.class);
    }

    @Provides
    public ScheduleFileDirectory provideScheduleFileDirectory(JsonReadService readService,
                                                              InteractionGroupService groupService,
                                                              XmlWriteService writeService,
                                                              StatsExportService exportService) {
        ScheduleFileDirectory fileDirectory = new ScheduleFileDirectory(readService, groupService, writeService, exportService);
        fileDirectory.setEnvironment(configuration.getEnvironmentConfig().getEnvironment());
        fileDirectory.setFileNamePattern(configuration.getEnvironmentConfig().getFileNamePattern());
        fileDirectory.setOutputPath(configuration.getEnvironmentConfig().getOutputPath());
        fileDirectory.setRootFolder(configuration.getEnvironmentConfig().getRootFolder());
        return fileDirectory;
    }

    @Provides
    public WatchFileDirectory provideWatchFileDirectory(JsonReadService readService, InteractionGroupService groupService, XmlWriteService writeService) {
        WatchFileDirectory fileDirectory = new WatchFileDirectory(readService, groupService, writeService);
        fileDirectory.setEnvironment(configuration.getEnvironmentConfig().getEnvironment());
        fileDirectory.setFileNamePattern(configuration.getEnvironmentConfig().getFileNamePattern());
        fileDirectory.setOutputPath(configuration.getEnvironmentConfig().getOutputPath());
        fileDirectory.setRootFolder(configuration.getEnvironmentConfig().getRootFolder());
        return fileDirectory;
    }

    @Provides
    public InternetConnectionHealthCheck provideConnectionHealthCheck() {
        InternetConnectionHealthCheck connectionHealthCheck = new InternetConnectionHealthCheck();
        connectionHealthCheck.setConnectionCheckUrl(configuration.getEnvironmentConfig().getHealthCheckConectionUrl());
        return connectionHealthCheck;
    }

    @Provides
    public MongoDatabase provideMongoDb() {
        MongoClient mongoClient = new MongoClient(configuration.getMongoConfig().getHost(),
                configuration.getMongoConfig().getPort());
        return mongoClient.getDatabase(configuration.getMongoConfig().getDatabase());
    }

    @Provides
    public Counter exportsCounter() {
        return environment.metrics().counter(name(XmlWriteServiceImpl.class, "exports amount"));
    }
}
