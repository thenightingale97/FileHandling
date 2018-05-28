package filemanager.service;

import filemanager.model.XmlModel;

import java.io.IOException;
import java.io.InputStream;

public interface JsonReader {
    XmlModel readJson(InputStream stream) throws IOException;

    String getClientFromJson(InputStream stream) throws IOException;
}
