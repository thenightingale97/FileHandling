package filemanager.binder;

import com.google.inject.*;
import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.XmlWriter;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;
import filemanager.serviceImpl.JsonReaderImpl;
import filemanager.serviceImpl.XmlWriterImpl;

public class FileServiceBinderModule extends AbstractModule {

    private FileHandlerConfiguration configuration;

    public FileServiceBinderModule(FileHandlerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(TrackerFileDirectory.class).to(ScheduleFileDirectory.class);
        bind(ConverterFromJsonToXmlService.class).to(ConverterFromJsonToXmlServiceImpl.class);
        bind(JsonReader.class).to(JsonReaderImpl.class);
        bind(XmlWriter.class).to(XmlWriterImpl.class);
    }

    @Provides
    public ScheduleFileDirectory provideConfigurationFields(ConverterFromJsonToXmlService service) {
        ScheduleFileDirectory fileDirectory = new ScheduleFileDirectory(service);
        fileDirectory.setEnvironment(configuration.getEnvironment());
        fileDirectory.setFileNamePattern(configuration.getFileNamePattern());
        fileDirectory.setOutputPath(configuration.getOutputPath());
        fileDirectory.setRootFolder(configuration.getRootFolder());
        return fileDirectory;
    }

}
