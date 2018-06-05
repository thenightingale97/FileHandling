package filemanager.binder;

import com.google.inject.*;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.healthchecks.InternetConnectionHealthCheck;
import filemanager.resource.ClientResource;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.InteractionGroupService;
import filemanager.service.JsonReadService;
import filemanager.service.XmlWriteService;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;
import filemanager.serviceImpl.InteractionGroupServiceImpl;
import filemanager.serviceImpl.JsonReadServiceImpl;
import filemanager.serviceImpl.XmlWriteServiceImpl;

public class FileServiceBinderModule extends AbstractModule {

    private FileHandlerConfiguration configuration;

    public FileServiceBinderModule(FileHandlerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(TrackerFileDirectory.class).to(ScheduleFileDirectory.class);
        bind(ConverterFromJsonToXmlService.class).to(ConverterFromJsonToXmlServiceImpl.class);
        bind(InteractionGroupService.class).to(InteractionGroupServiceImpl.class);
        bind(JsonReadService.class).to(JsonReadServiceImpl.class);
        bind(XmlWriteService.class).to(XmlWriteServiceImpl.class);
    }

    @Provides
    public ScheduleFileDirectory provideConfigurationFields(JsonReadService readService, InteractionGroupService groupService, XmlWriteService writeService) {
        ScheduleFileDirectory fileDirectory = new ScheduleFileDirectory(readService, groupService, writeService);
        fileDirectory.setEnvironment(configuration.getEnvironment());
        fileDirectory.setFileNamePattern(configuration.getFileNamePattern());
        fileDirectory.setOutputPath(configuration.getOutputPath());
        fileDirectory.setRootFolder(configuration.getRootFolder());
        return fileDirectory;
    }

    @Provides
    public ClientResource getClient(ScheduleFileDirectory scheduleFileDirectory) {
        ClientResource clientResource = new ClientResource(scheduleFileDirectory);
        return clientResource;
    }

    @Provides
    public InternetConnectionHealthCheck getConnectionHealthCheck() {
        InternetConnectionHealthCheck connectionHealthCheck = new InternetConnectionHealthCheck();
        connectionHealthCheck.setConnectionCheckUrl(configuration.getHealthCheckConectionUrl());
        return connectionHealthCheck;
    }
}
