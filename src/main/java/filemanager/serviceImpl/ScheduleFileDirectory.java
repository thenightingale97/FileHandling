package filemanager.serviceImpl;

import filemanager.service.ConvertionFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;

public class ScheduleFileDirectory {

    private String rootFolder;
    private String environment;
    private String outputPath;

    private ConvertionFromJsonToXmlService convertionService;

    public ScheduleFileDirectory(String rootFolder, String environment, String outputPath) {
        this.rootFolder = rootFolder;
        this.environment = environment;
        this.outputPath = outputPath;
        convertionService = new ConvertionFromJsonToXmlServiceImpl();
    }

    public void goThroughToCheckFile() throws IOException {
        Files.walk(Paths.get(rootFolder + environment), FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile).forEach(path -> {
            try {
                convertionService.readJsonConvertToXmlAndWrite(path, outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
