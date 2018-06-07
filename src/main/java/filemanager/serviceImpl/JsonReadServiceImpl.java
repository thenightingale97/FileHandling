package filemanager.serviceImpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import filemanager.model.Interaction;
import filemanager.service.JsonReadService;
import org.eclipse.jetty.util.IO;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class JsonReadServiceImpl implements JsonReadService {
    @Override
    public List<Interaction> readJson(InputStream stream) throws IOException {
        List<Interaction> interactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Interaction interaction = mapper.readValue(line, Interaction.class);
                    interactions.add(interaction);
                } catch (JsonParseException | JsonMappingException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return interactions;
    }
}
