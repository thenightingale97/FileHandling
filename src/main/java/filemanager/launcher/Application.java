package filemanager.launcher;

import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import filemanager.serviceImpl.JsonReaderImpl;
import filemanager.serviceImpl.JsonToXmlConverterImpl;
import filemanager.serviceImpl.XmlWriterImpl;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            JsonReader reader = new JsonReaderImpl();
            JsonToXmlConverter converter = new JsonToXmlConverterImpl();
            XmlWriter writer = new XmlWriterImpl();
            System.out.println("Enter path to file read from you need: ");
            Scanner sc = new Scanner(System.in);
            String pathToFile = sc.next();
            JSONObject jsonObject = reader.readJson(new FileInputStream(new File(pathToFile)));
            String client = reader.getClientFromJson(new FileInputStream(new File(pathToFile)));
            String xml = converter.convert(jsonObject);
            writer.writeXmlFile(xml, client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
