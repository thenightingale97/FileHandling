package filemanager.service.impl;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import filemanager.metrics.TaggedMetricName;
import filemanager.model.Feed;
import filemanager.model.Interaction;
import filemanager.service.XmlWriteService;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@NoArgsConstructor
public class XmlWriteServiceImpl implements XmlWriteService {

    private Map<String, Counter> counters;

    private MetricRegistry metricRegistry;

    @Inject
    public XmlWriteServiceImpl(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
        this.counters = new HashMap<>();
    }

    @Override
    public void writeXmlFile(List<Interaction> interactions, String outputPath) throws IOException {
        File file = new File(generateFileName(LocalDateTime.now(), outputPath));
        file.getParentFile().mkdirs();
        XmlMapper xmlMapper = new XmlMapper();
        Feed feed = new Feed(interactions);
        xmlMapper.writeValue(file, feed);
        Map<String, String> tags = new HashMap<>();
        interactions.forEach(interaction -> tags.put(Tags.CLIENT, interaction.getClientName()));
        TaggedMetricName exportMetricName = new TaggedMetricName(XmlWriteService.class, MetricType.EXPORT_COUNTER, tags);
        counters.put(exportMetricName.getName(), metricRegistry.counter(exportMetricName.getName()));
        counters.get(exportMetricName.getName()).inc(interactions.size());
    }

    @Override
    public String generateFileName(LocalDateTime time, String outputPath) throws FileAlreadyExistsException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        String rootName = outputPath + "/ppe/inbox/bvpixel-";
        String dateCreationName = time.format(formatter);
        String fileName = rootName + dateCreationName + ".xml";
        if (!Files.exists(Paths.get(fileName))) {
            return fileName;
        } else {
            throw new FileAlreadyExistsException("File with name - " + fileName + " already exists!");
        }
    }

    private static class MetricType {
        final static String EXPORT_COUNTER = "exports";
    }

    private static class Tags {
        final static String CLIENT = "client";
    }

}
