package filemanager.service.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import filemanager.model.Command;
import filemanager.service.JobWriterService;
import org.bson.Document;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JobWriterServiceImpl implements JobWriterService {

    private MongoDatabase database;

    @Inject
    public JobWriterServiceImpl(MongoDatabase database) {
        this.database = database;
    }

    @Override
    public void saveJobInformation(String clientName, Command command) {
        Date startTime = Date.from(LocalDateTime.now().atZone(Clock.systemUTC().getZone()).toInstant());
        Date targetTime = Date.from(command.getDate().atZone(Clock.systemUTC().getZone()).toInstant());
        Document job = new Document()
                .append("client", clientName)
                .append("start-time", startTime)
                .append("target-time", targetTime);
        MongoCollection<Document> collection = database.getCollection("jobInfo");
        collection.insertOne(job);
    }
}
