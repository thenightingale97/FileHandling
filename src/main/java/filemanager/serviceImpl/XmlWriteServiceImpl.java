package filemanager.serviceImpl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Singleton;
import filemanager.model.Interaction;
import filemanager.model.Interactions;
import filemanager.service.XmlWriteService;

import java.io.*;
import java.util.List;

@Singleton
public class XmlWriteServiceImpl implements XmlWriteService {

    @Override
    public void writeXmlFile(List<Interaction> interactions, String outputPath) throws IOException {
        File file = new File(outputPath + "/ppe/inbox/bvpixel-2018052113-1.xml");
        file.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
        XmlMapper xmlMapper = new XmlMapper();
        Interactions interactions1 = new Interactions();
        interactions1.setInteractions(interactions);
       /* interactions.forEach((interaction -> {
            try {
                xmlMapper.writeValue(fileWriter, interaction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));*/
        xmlMapper.writeValue(fileWriter, interactions);
    }
}
