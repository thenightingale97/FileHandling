package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.List;

public interface XmlWriteService {
    void writeXmlFile(List<Interaction> xml, String outputPath) throws IOException;

    String generateFileName(LocalDateTime time, String outputPath) throws FileAlreadyExistsException;

}
