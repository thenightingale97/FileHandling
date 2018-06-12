package filemanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class Job {

    private ObjectId id;

    private JobStatus status;

    private String client;

    private ZonedDateTime startHour;

    private ZonedDateTime endHour;

    private JobType jobType;

    private ZonedDateTime targetTime;
    
}
