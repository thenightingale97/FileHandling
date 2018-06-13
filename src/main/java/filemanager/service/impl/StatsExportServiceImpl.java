package filemanager.service.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import filemanager.model.ExportStats;
import filemanager.service.StatsExportService;
import lombok.NoArgsConstructor;
import org.bson.Document;

@NoArgsConstructor
public class StatsExportServiceImpl implements StatsExportService {

    private MongoCollection<Document> collection;

    @Inject
    public StatsExportServiceImpl(MongoDatabase database) {
        this.collection = database.getCollection("exportStats");
    }

    @Override
    public void updateStatsRecords(ExportStats exportStats) {
        Document document = jobToDocument(exportStats);
        collection.insertOne(document);

    }

    private Document jobToDocument(ExportStats stats) {
        Document document = new Document();
        document.put(Fields.CLIENT, stats.getClient());
        document.put(Fields.JOB_ID, stats.getJobId());
        document.put(Fields.AMOUNT, stats.getTransactionAmount());
        return document;
    }

    private static class Fields {
        static final String CLIENT = "client";
        static final String JOB_ID = "jobId";
        static final String AMOUNT= "count";
    }
}
