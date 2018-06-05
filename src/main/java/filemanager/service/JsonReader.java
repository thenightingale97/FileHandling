package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public interface JsonReader {
    List<Interaction> readJson(InputStream stream) throws IOException;
}
