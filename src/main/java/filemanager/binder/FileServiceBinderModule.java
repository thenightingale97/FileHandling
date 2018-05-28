package filemanager.binder;

import com.google.inject.AbstractModule;
import filemanager.controller.Application;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;
import filemanager.serviceImpl.JsonReaderImpl;
import filemanager.serviceImpl.JsonToXmlConverterImpl;
import filemanager.serviceImpl.XmlWriterImpl;

import java.util.concurrent.ScheduledExecutorService;

public class FileServiceBinderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TrackerFileDirectory.class).to(ScheduleFileDirectory.class);
        bind(Application.class);
        bind(ConverterFromJsonToXmlService.class).to(ConverterFromJsonToXmlServiceImpl.class);
        bind(JsonReader.class).to(JsonReaderImpl.class);
        bind(JsonToXmlConverter.class).to(JsonToXmlConverterImpl.class);
        bind(XmlWriter.class).to(XmlWriterImpl.class);
    }
}
