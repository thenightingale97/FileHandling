package filemanager.serviceImpl;

import filemanager.service.JsonFileReader;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReaderImpl implements JsonFileReader {
    @Override
    public JSONObject readJson(String path) throws IOException {
        String jsonObject = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        JSONObject jsonFileObject = new JSONObject(jsonObject);
        return jsonFileObject;
    }

    @Override
    public String getClientFromJson(String path) throws IOException {
        JSONObject jsonObject = readJson(path);
        String client = jsonObject.getString("client");
        System.out.println(client);
        return client;
    }
}
