package filemanager.controller;

import com.google.inject.Inject;
import filemanager.directorytracker.TrackerFileDirectory;


public class Application {

    private TrackerFileDirectory trackerFileDirectory;

    @Inject
    public Application(TrackerFileDirectory trackerFileDirectory) {
        this.trackerFileDirectory = trackerFileDirectory;
    }

    public void runProgram() {
        trackerFileDirectory.goThroughToCheckFile();
    }

}
