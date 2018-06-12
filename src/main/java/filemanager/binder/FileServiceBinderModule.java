package filemanager.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import filemanager.configuration.ApplicationConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.WatchFileDirectory;
import filemanager.healthchecks.InternetConnectionHealthCheck;
import filemanager.service.InteractionGroupService;
import filemanager.service.JobWriterService;
import filemanager.service.JsonReadService;
import filemanager.service.XmlWriteService;
import filemanager.service.impl.*;

public class FileServiceBinderModule extends AbstractModule {

    private ApplicationConfiguration configuration;

    public FileServiceBinderModule(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(InteractionGroupService.class).to(InteractionGroupServiceImpl.class);
        bind(JsonReadService.class).to(JsonReadServiceImpl.class);
        bind(XmlWriteService.class).to(XmlWriteServiceImpl.class);
        bind(FeedExporter.class);
        bind(JobWriterService.class).to(JobWriterServiceImpl.class);
    }

    @Provides
    public ScheduleFileDirectory provideScheduleFileDirectory(JsonReadService readService,
                                                              InteractionGroupService groupService,
                                                              XmlWriteService writeService) {
        ScheduleFileDirectory fileDirectory = new ScheduleFileDirectory(readService, groupService, writeService);
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
}
