package filemanager.serviceImpl;

import filemanager.service.XmlWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlWriterImpl implements XmlWriter {
    @Override
    public void writeXmlFile(String xml, String clientFolder) throws IOException {
        File file = new File("/home/ykato/Documents/" + clientFolder + "/ppe/inbox/bvpixel-2018052113-1.xml");
        file.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(file);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
