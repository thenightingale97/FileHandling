package filemanager.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import filemanager.model.Interaction;
import filemanager.service.JsonReader;

import java.io.*;

@Singleton
public class JsonReaderImpl implements JsonReader {
    @Override
    public Interaction readJson(InputStream stream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Interaction interaction = mapper.readValue(stream, Interaction.class);
        return interaction;
    }

}
