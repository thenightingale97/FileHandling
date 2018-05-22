package filemanager.service;

import java.io.IOException;

public interface XmlFileWriter {
    void writeXml(String xml, String path) throws IOException;
}
