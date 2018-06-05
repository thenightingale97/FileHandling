package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;
import java.util.List;

public interface XmlWriteService {
    void writeXmlFile(List<Interaction> xml, String outputPath) throws IOException;
}
