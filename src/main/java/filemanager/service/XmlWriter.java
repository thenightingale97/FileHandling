package filemanager.service;

import java.io.IOException;

public interface XmlWriter {
    void writeXmlFile(String xml, String clientFolder) throws IOException;
}
