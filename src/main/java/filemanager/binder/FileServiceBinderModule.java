package filemanager.binder;

import com.google.inject.AbstractModule;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlModelMapper;
import filemanager.service.XmlWriter;
import filemanager.serviceImpl.ConverterFromJsonToXmlServiceImpl;
import filemanager.serviceImpl.JsonReaderImpl;
import filemanager.serviceImpl.JsonToXmlModelMapperImpl;
import filemanager.serviceImpl.XmlWriterImpl;

public class FileServiceBinderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TrackerFileDirectory.class).to(ScheduleFileDirectory.class);
        bind(ConverterFromJsonToXmlService.class).to(ConverterFromJsonToXmlServiceImpl.class);
        bind(JsonReader.class).to(JsonReaderImpl.class);
        bind(XmlWriter.class).to(XmlWriterImpl.class);
        bind(JsonToXmlModelMapper.class).to(JsonToXmlModelMapperImpl.class);
    }
}
