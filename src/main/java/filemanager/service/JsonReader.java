package filemanager.service;

import filemanager.model.JsonModel;

import java.io.IOException;
import java.io.InputStream;

public interface JsonReader {
    JsonModel readJson(InputStream stream) throws IOException;

    String getClientFromJson(InputStream stream) throws IOException;
}
