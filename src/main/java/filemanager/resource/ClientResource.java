package filemanager.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import filemanager.KeyCheck;
import filemanager.model.Command;
import filemanager.model.JobType;
import filemanager.service.impl.FeedExporter;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/main")
@NoArgsConstructor
public class ClientResource {

    private FeedExporter feedExporter;

    @Inject
    public ClientResource(FeedExporter feedExporter) {
        this.feedExporter = feedExporter;
    }

    @POST
    @Timed(name = "checkForFiles")
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @KeyCheck
    @QueryParam("keyPass")
    public int checkForFiles(Command command) {
        command.setJobType(JobType.ON_DEMAND);
        feedExporter.startExport(command);
        return HttpStatus.OK_200;
    }

}