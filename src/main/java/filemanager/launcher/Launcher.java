package filemanager.launcher;

import filemanager.controller.Application;
import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.directorytracker.TrackerFileDirectory;
import filemanager.directorytracker.WatchFileDirectory;

import java.util.concurrent.TimeUnit;

public class Launcher {
    public static void main(String[] args) {
        Application application = new Application();
        TrackerFileDirectory watchFileDirectory = new WatchFileDirectory();
        TrackerFileDirectory scheduleFileDirectory = new ScheduleFileDirectory();

        application.runProgram(scheduleFileDirectory, 0, 1, TimeUnit.MINUTES);
    }

}