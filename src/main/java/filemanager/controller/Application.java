package filemanager.controller;

import com.google.inject.Inject;
import filemanager.directorytracker.TrackerFileDirectory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {

    private TrackerFileDirectory trackerFileDirectory;

    @Inject
    public Application(TrackerFileDirectory trackerFileDirectory) {
        this.trackerFileDirectory = trackerFileDirectory;
    }

    public void runProgram() {
        trackerFileDirectory.goThroughToCheckFile();
    }

    public void runProgram(long time1, long time2, TimeUnit timeUnit) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::runProgram, time1, time2, timeUnit);
    }
}
