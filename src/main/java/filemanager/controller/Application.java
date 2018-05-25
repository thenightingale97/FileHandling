package filemanager.controller;

import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.directorytracker.WatchFileDirectory;

public class Application {
    public void runProgram(TrackerFileDirectory trackerFileDirectory) {
        trackerFileDirectory.goThroughToCheckFile();
    }
}
