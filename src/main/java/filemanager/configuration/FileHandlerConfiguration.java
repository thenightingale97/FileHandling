package filemanager.configuration;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class FileHandlerConfiguration extends Configuration {
    @NotEmpty
    private String environment;

    @NotEmpty
    private String rootFolder;

    @NotEmpty
    private String outputPath;

    @NotEmpty
    private String fileNamePattern;

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
}
