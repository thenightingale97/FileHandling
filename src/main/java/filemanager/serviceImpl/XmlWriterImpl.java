package filemanager.serviceImpl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import filemanager.model.JsonModel;
import filemanager.model.XmlModel;
import filemanager.service.JsonToXmlModelMapper;
import filemanager.service.XmlWriter;

import java.io.File;
import java.io.IOException;

@Singleton
public class XmlWriterImpl implements XmlWriter {

    @Inject
    private JsonToXmlModelMapper mapper;

    @Override
    public void writeXmlFile(JsonModel jsonModel, String outputPath) throws IOException {
        File file = new File(outputPath + "/ppe/inbox/bvpixel-2018052113-1.xml");
        file.getParentFile().mkdirs();

        XmlModel xmlModel = mapper.map(jsonModel);
        
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(file, xmlModel);
    }
}
