package filemanager.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import filemanager.model.Command;
import filemanager.model.JobType;
import filemanager.service.impl.FeedExporter;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/main")
@NoArgsConstructor
public class ClientResource {

    private FeedExporter feedExporter;

    private RequestPassCheck check;

    @Inject
    public ClientResource(FeedExporter feedExporter, RequestPassCheck check) {
        this.feedExporter = feedExporter;
        this.check = check;
    }

    @POST
    @Timed(name = "checkForFiles")
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    public int checkForFiles(Command command) {
        check.checkPass(command);
        command.setJobType(JobType.ON_DEMAND);
        feedExporter.startExport(command);
        return HttpStatus.OK_200;
    }

}