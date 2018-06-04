package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Command {

    @NotEmpty
    private String date;

    private String client;

    @JsonProperty
    public String getDate() {
        return date;
    }

    @JsonProperty
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty
    public String getClient() {
        return client;
    }

    @JsonProperty
    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Command{" +
                "date='" + date + '\'' +
                ", client='" + client + '\'' +
                '}';
    }
}
