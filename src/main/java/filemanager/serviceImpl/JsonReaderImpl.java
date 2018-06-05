package filemanager.serviceImpl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import filemanager.model.Interaction;
import filemanager.service.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class JsonReaderImpl implements JsonReader {
    @Override
    public List<Interaction> readJson(InputStream stream) throws IOException {
        List<Interaction> interactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                Interaction interaction = mapper.readValue(line, Interaction.class);
                interactions.add(interaction);
            }
        }
        return interactions;
    }
}
