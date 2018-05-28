package filemanager.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import filemanager.model.JsonModel;
import filemanager.service.JsonReader;

import java.io.*;

@Singleton
public class JsonReaderImpl implements JsonReader {
    @Override
    public JsonModel readJson(InputStream stream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonModel jsonModel = mapper.readValue(stream, JsonModel.class);
        return jsonModel;
    }

    @Override
    public String getClientFromJson(InputStream stream) throws IOException {
        JsonModel jsonModel = readJson(stream);
        String client = jsonModel.getClientName();
        return client;
    }
}
