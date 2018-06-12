package filemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Command {

    private JobType jobType;

    @NotEmpty
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime date;

    @JsonProperty
    private String client;

}
