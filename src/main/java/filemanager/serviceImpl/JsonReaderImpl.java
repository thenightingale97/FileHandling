package filemanager.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import filemanager.model.XmlModel;
import filemanager.service.JsonReader;

import java.io.*;

@Singleton
public class JsonReaderImpl implements JsonReader {
    @Override
    public XmlModel readJson(InputStream stream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        XmlModel xmlModel = mapper.readValue(stream, XmlModel.class);
        return xmlModel;
    }

    @Override
    public String getClientFromJson(InputStream stream) throws IOException {
        XmlModel xmlModel = readJson(stream);
        String client = xmlModel.getClientName();
        return client;
    }
}
