package filemanager.service;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public interface JsonReader {
    JSONObject readJson(InputStream stream) throws IOException;

    String getClientFromJson(InputStream stream) throws IOException;
}
