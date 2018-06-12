package filemanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@NoArgsConstructor
@Getter
@Setter
public class ExportStats {

    private ObjectId id;

    private String client;

    private ObjectId jobId;

    private Integer transactionCount;
}
