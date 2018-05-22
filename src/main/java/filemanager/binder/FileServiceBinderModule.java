package filemanager.binder;

import com.google.inject.AbstractModule;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import filemanager.serviceImpl.JsonReaderImpl;
import filemanager.serviceImpl.JsonToXmlConverterImpl;
import filemanager.serviceImpl.XmlWriterImpl;

public class FileServiceBinderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JsonReader.class).to(JsonReaderImpl.class);
        bind(JsonToXmlConverter.class).to(JsonToXmlConverterImpl.class);
        bind(XmlWriter.class).to(XmlWriterImpl.class);
    }
}
