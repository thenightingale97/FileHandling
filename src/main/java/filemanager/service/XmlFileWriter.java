package filemanager.service;

import java.io.IOException;

public interface XmlFileWriter {
    void writeXmlFile(String xml, String clientFolder) throws IOException;
}
