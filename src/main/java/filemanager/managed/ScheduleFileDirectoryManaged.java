package filemanager.managed;

import filemanager.configuration.FileHandlerConfiguration;
import filemanager.directorytracker.ScheduleFileDirectory;
import io.dropwizard.lifecycle.Managed;

public class ScheduleFileDirectoryManaged implements Managed {

    private ScheduleFileDirectory scheduleFileDirectory;

    public ScheduleFileDirectoryManaged(ScheduleFileDirectory scheduleFileDirectory, FileHandlerConfiguration configuration) {
        this.scheduleFileDirectory = scheduleFileDirectory;
        scheduleFileDirectory.rootFolder = configuration.getRootFolder();
        scheduleFileDirectory.environment = configuration.getEnvironment();
        scheduleFileDirectory.outputPath = configuration.getOutputPath();
        scheduleFileDirectory.fileNamePattern = configuration.getFileNamePattern();
        scheduleFileDirectory.timeInterval = Long.valueOf(configuration.getTimeInterval());
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        scheduleFileDirectory.stopExecutorService();
    }
}
