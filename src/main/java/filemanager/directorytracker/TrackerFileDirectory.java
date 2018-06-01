package filemanager.directorytracker;

import java.time.LocalDateTime;

public abstract class TrackerFileDirectory {

    private String rootFolder;
    private String environment;
    private String outputPath;
    private String fileNamePattern;
    private Long timeInterval;

    abstract public void goThroughToCheckFile();

    abstract public void goThroughToCheckFile(LocalDateTime time);

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public Long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Long timeInterval) {
        this.timeInterval = timeInterval;
    }
}
