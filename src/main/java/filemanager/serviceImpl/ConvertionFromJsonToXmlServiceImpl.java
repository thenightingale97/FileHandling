package filemanager.serviceImpl;

import com.google.inject.Inject;
import filemanager.service.ConvertionFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ConvertionFromJsonToXmlServiceImpl implements ConvertionFromJsonToXmlService {

    @Inject
    private JsonReader reader;

    @Inject
    private JsonToXmlConverter converter;

    @Inject
    private XmlWriter writer;

    public ConvertionFromJsonToXmlServiceImpl() {
        reader = new JsonReaderImpl();
        converter = new JsonToXmlConverterImpl();
        writer = new XmlWriterImpl();
    }

    @Override
    public void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException {
        JSONObject jsonObject = reader.readJson(new FileInputStream(String.valueOf(from)));
        String client = reader.getClientFromJson(new FileInputStream(String.valueOf(from)));
        String temporaryPath = to + client;
        String xml = converter.convert(jsonObject);
        writer.writeXmlFile(xml, temporaryPath);
    }

}
