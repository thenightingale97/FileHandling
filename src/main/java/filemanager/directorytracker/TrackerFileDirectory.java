package filemanager.directorytracker;

import java.time.LocalDateTime;

public abstract class TrackerFileDirectory {

    public String rootFolder;
    public String environment;
    public String outputPath;
    public String fileNamePattern;
    public Long timeInterval;

    abstract public void goThroughToCheckFile();

    abstract public void goThroughToCheckFile(LocalDateTime time);

}
