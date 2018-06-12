package filemanager.service;

import filemanager.model.Job;
import org.bson.types.ObjectId;

public interface JobWriterService {

    ObjectId saveJob(Job job);

    void updateJob(Job job, ObjectId jobId);

}
