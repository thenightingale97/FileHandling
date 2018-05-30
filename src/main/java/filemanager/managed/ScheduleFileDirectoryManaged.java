package filemanager.managed;

import filemanager.directorytracker.ScheduleFileDirectory;
import io.dropwizard.lifecycle.Managed;

public class ScheduleFileDirectoryManaged implements Managed {

    private ScheduleFileDirectory scheduleFileDirectory;

    public ScheduleFileDirectoryManaged(ScheduleFileDirectory scheduleFileDirectory) {
        this.scheduleFileDirectory = scheduleFileDirectory;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        scheduleFileDirectory.stopExecutorService();
    }
}
