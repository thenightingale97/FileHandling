package filemanager.service;

import org.json.JSONObject;

import java.io.IOException;

public interface JsonFileReader {
    JSONObject readJson(String path) throws IOException;

    String getClientFromJson(String path) throws IOException;
}
