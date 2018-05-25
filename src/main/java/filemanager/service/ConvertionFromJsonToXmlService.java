package filemanager.service;

import java.io.IOException;
import java.nio.file.Path;

public interface ConvertionFromJsonToXmlService {
    void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException;
}
