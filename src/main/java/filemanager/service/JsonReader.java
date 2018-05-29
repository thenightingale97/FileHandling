package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;
import java.io.InputStream;

public interface JsonReader {
    Interaction readJson(InputStream stream) throws IOException;
}
