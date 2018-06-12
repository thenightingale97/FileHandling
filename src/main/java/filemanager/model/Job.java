package filemanager.model;

import org.bson.types.ObjectId;

import java.time.ZonedDateTime;

public class Job {

    private ObjectId id;

    private JobStatus status;

    private String client;

    private ZonedDateTime startHour;

    private ZonedDateTime endHour;

    private JobType jobType;

    private ZonedDateTime targetTime;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ZonedDateTime getStartHour() {
        return startHour;
    }

    public void setStartHour(ZonedDateTime startHour) {
        this.startHour = startHour;
    }

    public ZonedDateTime getEndHour() {
        return endHour;
    }

    public void setEndHour(ZonedDateTime endHour) {
        this.endHour = endHour;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public ZonedDateTime getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(ZonedDateTime targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public String toString() {
        return "Job{" +
                "status=" + status +
                ", client='" + client + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", jobType=" + jobType +
                ", targetTime=" + targetTime +
                '}';
    }
}
