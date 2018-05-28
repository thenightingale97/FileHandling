package filemanager.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import filemanager.model.XmlModel;
import filemanager.service.XmlWriter;

import java.io.*;

@Singleton
public class XmlWriterImpl implements XmlWriter {

    @Override
    public void writeXmlFile(XmlModel xmlModel, String outputPath) throws IOException {
        File file = new File(outputPath + "/ppe/inbox/bvpixel-2018052113-1.xml");
        file.getParentFile().mkdirs();
        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.writeValue(file, xmlModel);
        System.out.println(xmlModel);
    }
}
