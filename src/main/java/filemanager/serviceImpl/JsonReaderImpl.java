package filemanager.serviceImpl;

import filemanager.service.JsonReader;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class JsonReaderImpl implements JsonReader {
    @Override
    public JSONObject readJson(InputStream stream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(stream))) {
            String jsonObject = buffer.lines().collect(Collectors.joining(System.lineSeparator()));
            JSONObject jsonFileObject = new JSONObject(jsonObject);
            return jsonFileObject;
        }
    }

    @Override
    public String getClientFromJson(InputStream stream) throws IOException {
        JSONObject jsonObject = readJson(stream);
        String client = jsonObject.getString("client");
        System.out.println(client);
        return client;
    }
}
