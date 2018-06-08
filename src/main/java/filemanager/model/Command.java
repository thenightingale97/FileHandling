package filemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

@Getter
@ToString
public class Command {

    @NotEmpty
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime date;

    @JsonProperty
    private String client;

    public Command setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Command setClient(String client) {
        this.client = client;
        return this;
    }
}
