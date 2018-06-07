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
        Command command = new Command();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String dateStr = LocalDateTime.now().format(formatter);
        command.setDate(dateStr);
        this.goThroughToCheckFile(command);
    }

    public void goThroughToCheckFile(Command command) {
        interactionsMap = new HashMap<>();
        interactions = new ArrayList<>();
        String time = command.getDate();
        String commandClientName = command.getClient();
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
                                interactions.addAll(readService.readJson(new FileInputStream(path.toString())));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            groupService.groupByClient(interactions, interactionsMap);
            if (commandClientName == null || commandClientName.isEmpty()) {
                interactionsMap.forEach((clientName, interactionList) -> {
                    String temporaryPath = getOutputPath() + clientName;
                    try {
                        writeService.writeXmlFile(interactionList, temporaryPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                interactionsMap.forEach((clientName, interactionList) -> {
                    if (clientName.equalsIgnoreCase(commandClientName)) {
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
    }

    private boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(getFileNamePattern());
    }

}
