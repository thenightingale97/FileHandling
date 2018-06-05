package filemanager.serviceImpl;

import com.google.inject.Inject;
import filemanager.model.Command;
import filemanager.model.Interaction;
import filemanager.service.ConverterFromJsonToXmlService;
import filemanager.service.JsonReader;
import filemanager.service.XmlWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ConverterFromJsonToXmlServiceImpl implements ConverterFromJsonToXmlService {

    private JsonReader reader;

    private XmlWriter writer;

    @Inject
    public ConverterFromJsonToXmlServiceImpl(JsonReader reader, XmlWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void readJsonConvertToXmlAndWrite(Path from, String to) throws IOException {
        List<Interaction> interactions = reader.readJson(new FileInputStream(String.valueOf(from)));
        interactions.forEach((interaction) -> {
            String client = interaction.getClientName();
            String temporaryPath = to + client;
            try {
                writer.writeXmlFile(interaction, temporaryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void readJsonConvertToXmlAndWrite(Path from, String to, Command command) throws IOException {
        List<Interaction> interactions = reader.readJson(new FileInputStream(String.valueOf(from)));
        interactions.forEach((interaction) -> {
            if (interaction.getClientName().equalsIgnoreCase(command.getClient())) {
                String client = interaction.getClientName();
                String temporaryPath = to + client;
                try {
                    writer.writeXmlFile(interaction, temporaryPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}