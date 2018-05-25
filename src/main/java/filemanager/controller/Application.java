package filemanager.controller;

import filemanager.directorytracker.TrackerFileDirectory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {
    public void runProgram(TrackerFileDirectory trackerFileDirectory) {
        trackerFileDirectory.goThroughToCheckFile();
    }

    public void runProgram(TrackerFileDirectory trackerFileDirectory, long time1, long time2, TimeUnit timeUnit) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> runProgram(trackerFileDirectory), time1, time2, timeUnit);
    }
}
