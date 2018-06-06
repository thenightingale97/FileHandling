package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.model.Command;
import filemanager.model.Interaction;
import filemanager.service.InteractionGroupService;
import filemanager.service.JsonReadService;
import filemanager.service.XmlWriteService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    private JsonReadService readService;

    private InteractionGroupService groupService;

    private XmlWriteService writeService;

    private HashMap<String, List<Interaction>> interactionsMap;

    private List<Interaction> interactions;

    @Inject
    public ScheduleFileDirectory(JsonReadService readService, InteractionGroupService groupService, XmlWriteService writeService) {
        this.readService = readService;
        this.groupService = groupService;
        this.writeService = writeService;
    }

    public void goThroughToCheckFile() {
        this.goThroughToCheckFile(LocalDateTime.now());
    }

    public void goThroughToCheckFile(LocalDateTime time) {
        interactionsMap = new HashMap<>();
        interactions = new ArrayList<>();
        Path allPath = Paths.get(getRootFolder() + getEnvironment() + "/" +
                time.getYear() + "/" +
                time.getMonthValue() + "/" +
                time.getDayOfMonth() + "/" +
                time.getHour());
        if (Files.exists(allPath)) {
            try {
                Files.walk(allPath, FileVisitOption.FOLLOW_LINKS)
                        .filter(file -> Files.isRegularFile(file) && matchPattern(file.toString()))
                        .forEach(path -> {
                            try {
                                readService.readJson(new FileInputStream(path.toString()), interactions);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            groupService.groupByClient(interactions, interactionsMap);
            interactionsMap.forEach((clientName, interactions1) -> {
                String temporaryPath = getOutputPath() + clientName;
                try {
                    writeService.writeXmlFile(interactions1, temporaryPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    public void goThroughToCheckFile(Command command) {
        interactionsMap = new HashMap<>();
        interactions = new ArrayList<>();
        String time = command.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        Path allPath = Paths.get(getRootFolder() + getEnvironment() + "/" +
                dateTime.getYear() + "/" +
                dateTime.getMonthValue() + "/" +
                dateTime.getDayOfMonth() + "/" +
                dateTime.getHour());
        if (Files.exists(allPath)) {
            try {
                Files.walk(allPath, FileVisitOption.FOLLOW_LINKS)
                        .filter((file) -> Files.isRegularFile(file) && matchPattern(file.toString()))
                        .forEach(path -> {
                            try {
                                readService.readJson(new FileInputStream(path.toString()), interactions);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            groupService.groupByClient(interactions, interactionsMap);
            interactionsMap.forEach((clientName, interactionList) -> {
                if (clientName.equalsIgnoreCase(command.getClient())) {
                    String temporaryPath = getOutputPath() + clientName;
                    try {
                        writeService.writeXmlFile(interactionList, temporaryPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(getFileNamePattern());
    }

}
