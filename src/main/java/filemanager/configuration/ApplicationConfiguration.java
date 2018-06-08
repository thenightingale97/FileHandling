package filemanager.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {

    private MongoConfiguration mongoConfig;

    private EnvironmentConfiguration environmentConfig;

    @JsonProperty
    public MongoConfiguration getMongoConfig() {
        return mongoConfig;
    }

    @JsonProperty
    public void setMongoConfig(MongoConfiguration mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    @JsonProperty
    public EnvironmentConfiguration getEnvironmentConfig() {
        return environmentConfig;
    }

    @JsonProperty
    public void setEnvironmentConfig(EnvironmentConfiguration environmentConfig) {
        this.environmentConfig = environmentConfig;
    }
}
