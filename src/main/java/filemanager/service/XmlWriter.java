package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;

public interface XmlWriter {
    void writeXmlFile(Interaction xml, String outputPath) throws IOException;
}
