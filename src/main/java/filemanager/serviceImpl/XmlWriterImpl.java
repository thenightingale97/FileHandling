package filemanager.serviceImpl;

import com.google.inject.Singleton;
import filemanager.service.XmlWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Singleton
public class XmlWriterImpl implements XmlWriter {
    @Override
    public void writeXmlFile(String xml, String outputPath) throws IOException {
        File file = new File(outputPath + "/ppe/inbox/bvpixel-2018052113-1.xml");
        file.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(file);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
