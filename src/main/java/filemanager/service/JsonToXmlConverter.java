package filemanager.service;

import filemanager.model.JsonModel;

public interface JsonToXmlConverter {
    String convert(JsonModel jsonModel);
}
