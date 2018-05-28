package filemanager.service;

import filemanager.model.JsonModel;
import filemanager.model.XmlModel;

public interface JsonToXmlModelMapper {
    XmlModel map(JsonModel jsonModel);
}
