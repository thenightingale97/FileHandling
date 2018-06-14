package filemanager.service.impl;

import com.codahale.metrics.Counter;
import com.google.inject.Inject;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.model.Command;
import filemanager.model.Job;
import filemanager.model.JobStatus;
import filemanager.service.JobWriterService;
import org.bson.types.ObjectId;
import org.eclipse.jetty.util.Fields;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Map;

public class FeedExporter {

    private ScheduleFileDirectory scheduleFileDirectory;

    private JobWriterService writerService;

    private Map<String, Counter> counters;

    @Inject
    public FeedExporter(ScheduleFileDirectory scheduleFileDirectory,
                        JobWriterService writerService,
                        Map<String, Counter> counters) {
        this.scheduleFileDirectory = scheduleFileDirectory;
        this.writerService = writerService;
        this.counters = counters;
    }

    public void startExport(Command command) {
        Job job = new Job();
        job.setStartHour(ZonedDateTime.now());
        job.setTargetTime(ZonedDateTime.from(command.getDate().atZone(Clock.systemUTC().getZone())));
        job.setStatus(JobStatus.RUNNING);
        job.setJobType(command.getJobType());

        ObjectId jobId = writerService.saveJob(job);
        job.setId(jobId);
        try {
            switch (command.getJobType()) {
                case SCHEDULED:
                    scheduleFileDirectory.goThroughToCheckFile(job);
                    job.setEndHour(ZonedDateTime.now());
                    break;
                case ON_DEMAND:
                    job.setClient(command.getClient());
                    scheduleFileDirectory.goThroughToCheckFile(job);
                    job.setEndHour(ZonedDateTime.now());
                    job.setTargetTime(job.getTargetTime());
            }
            job.setStatus(JobStatus.COMPLETE);
            counters.get(Fields.COMPLETED_TRANSACTION_COUNTER).inc();
        } catch (Exception e) {
            e.printStackTrace();
            job.setStatus(JobStatus.FAILED);
            counters.get(Fields.FAILED_TRANSACTION_COUNTER).inc();
        } finally {
            writerService.updateJob(job);
            counters.get(Fields.TRANSACTION_COUNTER).inc();
        }
    }

    private static class Fields {
        final static String FAILED_TRANSACTION_COUNTER = "completedTransactionCounter";
        final static String COMPLETED_TRANSACTION_COUNTER = "failedTransactionCounter";
        final static String TRANSACTION_COUNTER = "transactionCounter";
    }


}
