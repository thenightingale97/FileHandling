package filemanager.directorytracker;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import filemanager.metrics.TaggedMetricName;
import filemanager.model.ExportStats;
import filemanager.model.Interaction;
import filemanager.model.Job;
import filemanager.service.InteractionGroupService;
import filemanager.service.JsonReadService;
import filemanager.service.StatsExportService;
import filemanager.service.XmlWriteService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFileDirectory extends TrackerFileDirectory {

    private JsonReadService readService;

    private InteractionGroupService groupService;

    private XmlWriteService writeService;

    private StatsExportService statsExportService;

    private MetricRegistry metricRegistry;

    private HashMap<String, List<Interaction>> interactionsMap;

    private List<Interaction> interactions;

    private Map<String, Counter> counters;


    @Inject
    public ScheduleFileDirectory(JsonReadService readService,
                                 InteractionGroupService groupService,
                                 XmlWriteService writeService,
                                 StatsExportService statsExportService,
                                 MetricRegistry metricRegistry) {
        this.readService = readService;
        this.groupService = groupService;
        this.writeService = writeService;
        this.counters = new HashMap<>();
        this.metricRegistry = metricRegistry;
        this.statsExportService = statsExportService;
    }

    public void goThroughToCheckFile(Job job) {
        interactionsMap = new HashMap<>();
        interactions = new ArrayList<>();
        LocalDateTime time = LocalDateTime.ofInstant(job.getTargetTime().toInstant(), Clock.systemUTC().getZone());
        String commandClientName = job.getClient();
        Path allPath = Paths.get(getRootFolder() + getEnvironment() + "/" +
                time.getYear() + "/" +
                time.getMonthValue() + "/" +
                time.getDayOfMonth() + "/" +
                time.getHour());
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
            interactionsMap.putAll(groupService.groupByClient(interactions));
            interactionsMap.forEach((clientName, interactionList) -> {
                if (clientName.equalsIgnoreCase(commandClientName) || commandClientName == null || commandClientName.isEmpty()) {
                    String temporaryPath = getOutputPath() + clientName;
                    try {
                        writeService.writeXmlFile(interactionList, temporaryPath);
                        Map<String, String> tags = new HashMap<>();
                        tags.put(Tags.CLIENT, clientName);
                        TaggedMetricName exportMetricName = new TaggedMetricName(XmlWriteService.class, MetricType.EXPORT_COUNTER, tags);
                        counters.putIfAbsent(exportMetricName.getName(), metricRegistry.counter(exportMetricName.getName()));
                        counters.get(exportMetricName.getName()).inc(interactionList.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ExportStats stats = new ExportStats();
                    stats.setClient(clientName);
                    stats.setJobId(job.getId());
                    stats.setTransactionAmount(interactionList.size());
                    statsExportService.updateStatsRecords(stats);
                }

            });
        }
    }

    private static class MetricType {
        final static String EXPORT_COUNTER = "exports";
    }

    private static class Tags {
        final static String CLIENT = "client";
    }

    private boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(getFileNamePattern());
    }

}