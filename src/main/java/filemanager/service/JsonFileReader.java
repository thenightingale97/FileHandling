package filemanager.service;

import org.json.JSONObject;

public interface JsonFileReader {
    JSONObject readJson(String path);
}
