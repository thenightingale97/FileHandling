package filemanager.service;

import filemanager.model.XmlModel;

import java.io.IOException;

public interface XmlWriter {
    void writeXmlFile(XmlModel xml, String outputPath) throws IOException;
}
