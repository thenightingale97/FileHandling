package filemanager.controller;

import com.google.inject.Inject;
import filemanager.directorytracker.TrackerFileDirectory;

import java.time.LocalDateTime;


public class Application {

    private TrackerFileDirectory trackerFileDirectory;

    @Inject
    public Application(TrackerFileDirectory trackerFileDirectory) {
        this.trackerFileDirectory = trackerFileDirectory;
    }

    public void runProgram() {
        trackerFileDirectory.goThroughToCheckFile();
    }

    public void runProgram(LocalDateTime time) {
        trackerFileDirectory.goThroughToCheckFile(time);
    }


}
