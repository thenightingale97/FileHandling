package filemanager.service.impl;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.metrics.TaggedMetricName;
import filemanager.model.Command;
import filemanager.model.Job;
import filemanager.model.JobStatus;
import filemanager.service.JobWriterService;
import org.bson.types.ObjectId;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class FeedExporter {

    private ScheduleFileDirectory scheduleFileDirectory;

    private JobWriterService writerService;

    private Map<String, Counter> counters;

    private MetricRegistry metricRegistry;

    @Inject
    public FeedExporter(ScheduleFileDirectory scheduleFileDirectory,
                        JobWriterService writerService,
                        MetricRegistry metricRegistry) {
        this.scheduleFileDirectory = scheduleFileDirectory;
        this.writerService = writerService;
        this.metricRegistry = metricRegistry;
        this.counters = new HashMap<>();
    }

    public void startExport(Command command) {
        Job job = new Job();
        job.setStartHour(ZonedDateTime.now());
        job.setTargetTime(ZonedDateTime.from(command.getDate().atZone(Clock.systemUTC().getZone())));
        job.setStatus(JobStatus.RUNNING);
        job.setJobType(command.getJobType());

        ObjectId jobId = writerService.saveJob(job);
        job.setId(jobId);
        Map<String, String> tags = new HashMap<>();
        tags.put(Tags.JOB_TYPE, job.getJobType().name());
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
            TaggedMetricName jobMetricName = new TaggedMetricName(FeedExporter.class, Fields.COMPLETED_TRANSACTION_COUNTER, tags);
            counters.putIfAbsent(jobMetricName.getName(), metricRegistry.counter(jobMetricName.getName()));
            counters.get(jobMetricName.getName()).inc();
        } catch (Exception e) {
            e.printStackTrace();
            job.setStatus(JobStatus.FAILED);
            TaggedMetricName jobMetricName = new TaggedMetricName(FeedExporter.class, Fields.FAILED_TRANSACTION_COUNTER, tags);
            counters.putIfAbsent(jobMetricName.getName(), metricRegistry.counter(jobMetricName.getName()));
            counters.get(jobMetricName.getName()).inc();
        } finally {
            writerService.updateJob(job);
            TaggedMetricName jobMetricName = new TaggedMetricName(FeedExporter.class, Fields.TRANSACTION_COUNTER, tags);
            counters.putIfAbsent(jobMetricName.getName(), metricRegistry.counter(jobMetricName.getName()));
            counters.get(jobMetricName.getName()).inc();
        }
    }

    private static class Fields {
        final static String FAILED_TRANSACTION_COUNTER = "failedTransaction";
        final static String COMPLETED_TRANSACTION_COUNTER = "completedTransaction";
        final static String TRANSACTION_COUNTER = "transaction";
    }

    private static class Tags {
        final static String JOB_TYPE = "jobType";
    }


}
