package filemanager.service.impl;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import filemanager.model.ExportStats;
import filemanager.service.StatsExportService;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@NoArgsConstructor
public class StatsExportServiceImpl implements StatsExportService {

    private MongoCollection<Document> collection;

    @Inject
    public StatsExportServiceImpl(MongoDatabase database) {
        this.collection = database.getCollection("exportStats");
    }

    @Override
    public void updateStatsRecords(ExportStats exportStats) {
        FindIterable<Document> cursor = collection.find(
                and(
                        eq(Fields.JOB_ID, exportStats.getJobId()),
                        eq(Fields.CLIENT, exportStats.getClient())));

        Iterator<Document> iterator = cursor.iterator();

        if (iterator.hasNext()) {
            BasicDBObject updateFields = new BasicDBObject();
            int count = (int) iterator.next().get(Fields.COUNT);
            updateFields.append(Fields.COUNT, ++count);
            BasicDBObject setQuery = new BasicDBObject();
            setQuery.append("$set", updateFields);
            collection.updateOne(eq(Fields.CLIENT, exportStats.getClient()), setQuery);
        } else {
            Document document = jobToDocument(exportStats);
            collection.insertOne(document);
        }
    }

    private Document jobToDocument(ExportStats stats) {
        Document document = new Document();
        document.put(Fields.CLIENT, stats.getClient());
        document.put(Fields.JOB_ID, stats.getJobId());
        document.put(Fields.COUNT, 1);
        return document;
    }

    private static class Fields {
        static final String CLIENT = "client";
        static final String JOB_ID = "jobId";
        static final String COUNT = "count";
    }
}
