package filemanager.configuration;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class EnvironmentConfiguration {
    @NotEmpty
    private String environment;

    @NotEmpty
    private String rootFolder;

    @NotEmpty
    private String outputPath;

    @NotEmpty
    private String fileNamePattern;

    @NotEmpty
    private String timeInterval;

    private String healthCheckConectionUrl;

    @JsonProperty
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @JsonProperty
    public String getRootFolder() {
        return rootFolder;
    }

    @JsonProperty
    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    @JsonProperty
    public String getOutputPath() {
        return outputPath;
    }

    @JsonProperty
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    @JsonProperty
    public String getFileNamePattern() {
        return fileNamePattern;
    }

    @JsonProperty
    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    @JsonProperty
    public String getTimeInterval() {
        return timeInterval;
    }

    @JsonProperty
    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    @JsonProperty
    public String getHealthCheckConectionUrl() {
        return healthCheckConectionUrl;
    }

    @JsonProperty
    public void setHealthCheckConectionUrl(String healthCheckConectionUrl) {
        this.healthCheckConectionUrl = healthCheckConectionUrl;
    }

}
