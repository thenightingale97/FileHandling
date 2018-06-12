package filemanager.service.impl;

import com.google.inject.Inject;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.model.Command;
import filemanager.model.Job;
import filemanager.model.JobStatus;
import filemanager.service.JobWriterService;
import org.bson.types.ObjectId;

import java.time.Clock;
import java.time.ZonedDateTime;

public class FeedExporter {

    private ScheduleFileDirectory scheduleFileDirectory;

    private JobWriterService writerService;

    @Inject
    public FeedExporter(ScheduleFileDirectory scheduleFileDirectory, JobWriterService writerService) {
        this.scheduleFileDirectory = scheduleFileDirectory;
        this.writerService = writerService;
    }

    public void startExport(Command command) {
        Job job = new Job();
        job.setStartHour(ZonedDateTime.now());
        job.setTargetTime(ZonedDateTime.from(command.getDate().atZone(Clock.systemUTC().getZone())));
        job.setStatus(JobStatus.RUNNING);
        job.setJobType(command.getJobType());

        ObjectId jobId = writerService.saveJob(job);
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
        } finally {
            job.setStatus(JobStatus.FAILED);
            writerService.updateJob(job, jobId);
        }
        job.setStatus(JobStatus.COMPLETE);
        writerService.updateJob(job, jobId);
    }


}
