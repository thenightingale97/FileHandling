package filemanager.serviceImpl;

import com.google.inject.Inject;
import filemanager.model.JsonModel;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.XmlWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ConverterFromJsonToXmlServiceImpl implements ConverterFromJsonToXmlService {

    private JsonReader reader;

    private XmlWriter writer;

    @Inject
    public ConverterFromJsonToXmlServiceImpl(JsonReader reader, XmlWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException {
        JsonModel jsonModel = reader.readJson(new FileInputStream(String.valueOf(from)));
        String client = reader.getClientFromJson(new FileInputStream(String.valueOf(from)));
        String temporaryPath = to + client;
        writer.writeXmlFile(jsonModel, temporaryPath);
    }

}
