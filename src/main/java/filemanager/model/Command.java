package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Command {

    @NotEmpty
    private String date;

    @JsonProperty
    public String getDate() {
        return date;
    }

    @JsonProperty
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Command{" +
                "date='" + date + '\'' +
                '}';
    }
}
