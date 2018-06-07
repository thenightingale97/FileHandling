package filemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Command {

    @NotEmpty
    private LocalDateTime date;

    private String client;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    public LocalDateTime getDate() {
        return date;
    }

    @JsonProperty
    public void setDate(LocalDateTime date) {
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
