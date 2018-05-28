package filemanager.serviceImpl;

import filemanager.model.JsonModel;
import filemanager.model.JsonProductModel;
import filemanager.model.XmlModel;
import filemanager.model.XmlProductModel;
import filemanager.service.JsonToXmlModelMapper;

import java.util.ArrayList;

public class JsonToXmlModelMapperImpl implements JsonToXmlModelMapper {
    @Override
    public XmlModel map(JsonModel jsonModel) {
        XmlModel xmlModel = new XmlModel();
        xmlModel.setEmail(jsonModel.getEmail());
        xmlModel.setClientName(jsonModel.getClientName());
        xmlModel.setExternalId(jsonModel.getExternalId());
        xmlModel.setTransactionTime(jsonModel.getTransactionTime());
        ArrayList<XmlProductModel> xmlProductModels = new ArrayList<>();
        for (JsonProductModel jsonProductModel : jsonModel.getProducts()) {
            XmlProductModel xmlProductModel = new XmlProductModel();
            xmlProductModel.setExternalId(jsonProductModel.getSku());
            xmlProductModel.setImageUrl(jsonProductModel.getImageUrl());
            xmlProductModel.setName(jsonProductModel.getName());
            xmlProductModel.setPrice(jsonProductModel.getPrice());
            xmlProductModels.add(xmlProductModel);
        }
        xmlModel.setProduct(xmlProductModels);
        return xmlModel;
    }
}
