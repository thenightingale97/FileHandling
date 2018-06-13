package filemanager.service.impl;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import filemanager.model.Job;
import filemanager.service.JobWriterService;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class JobWriterServiceImpl implements JobWriterService {

    private MongoCollection<Document> collection;

    public JobWriterServiceImpl() {
    }

    @Inject
    public JobWriterServiceImpl(MongoDatabase database) {
        this.collection = database.getCollection("jobInfo");
    }

    @Override
    public ObjectId saveJob(Job job) {
        Document document = jobToDocument(job);
        collection.insertOne(document);
        return (ObjectId) document.get(Fields.ID);
    }

    @Override
    public void updateJob(Job job) {
        BasicDBObject updateFields = new BasicDBObject();
        if (job.getClient() != null && !job.getClient().isEmpty()) {
            updateFields.append(Fields.CLIENT, job.getClient());
        }
        if (job.getTargetTime() != null) {
            updateFields.append(Fields.TARGET_TIME, Date.from(job.getTargetTime().toInstant()));
        }
        if (job.getEndHour() != null) {
            updateFields.append(Fields.END_HOUR, Date.from(ZonedDateTime.now().toInstant()));
        }
        if (job.getStatus() != null) {
            updateFields.append(Fields.STATUS, job.getStatus().toString());
        }

        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateFields);
        collection.updateOne(eq(Fields.ID, job.getId()), setQuery);

    }

    private Document jobToDocument(Job job) {
        Document document = new Document();
        document.put(Fields.START_HOUR, Date.from(job.getStartHour().toInstant()));
        document.put(Fields.STATUS, job.getStatus().toString());
        document.put(Fields.TYPE, job.getJobType().toString());
        return document;
    }

    private static class Fields {
        static final String START_HOUR = "startHour";
        static final String TARGET_TIME = "targetTime";
        static final String END_HOUR = "endHour";
        static final String STATUS = "status";
        static final String TYPE = "type";
        static final String CLIENT = "client";
        static final String ID = "_id";
    }
}
