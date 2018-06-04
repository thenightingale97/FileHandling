package filemanager.service;

import filemanager.model.Command;

import java.io.IOException;
import java.nio.file.Path;

public interface ConverterFromJsonToXmlService {
    void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException;

    void readJsonConvertToXmlAndWrite(Path from, String to, Command command) throws IOException;
}