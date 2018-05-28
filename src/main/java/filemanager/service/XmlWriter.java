package filemanager.service;

import filemanager.model.JsonModel;

import java.io.IOException;

public interface XmlWriter {
    void writeXmlFile(JsonModel xml, String outputPath) throws IOException;
}
