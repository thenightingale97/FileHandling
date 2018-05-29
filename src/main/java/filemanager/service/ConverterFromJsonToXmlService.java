package filemanager.service;

import java.io.IOException;
import java.nio.file.Path;

public interface ConverterFromJsonToXmlService {
    void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException;
}
