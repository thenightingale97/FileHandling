package filemanager.serviceImpl;

import com.google.inject.Singleton;
import filemanager.model.JsonModel;
import filemanager.service.JsonToXmlConverter;
import org.json.XML;


@Singleton
public class JsonToXmlConverterImpl implements JsonToXmlConverter {
    @Override
    public String convert(JsonModel jsonModel) {
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<" + "root" + ">"
                + XML.toString(jsonModel) + "</" + "root" + ">";
        return xml;
    }
}
