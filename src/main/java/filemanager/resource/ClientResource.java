package filemanager.resource;

import filemanager.directorytracker.ScheduleFileDirectory;
import filemanager.model.Command;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/main")
public class ClientResource {

    private ScheduleFileDirectory scheduleFileDirectory;

    public ClientResource() {
    }

    public ClientResource(ScheduleFileDirectory scheduleFileDirectory) {
        this.scheduleFileDirectory = scheduleFileDirectory;
    }

    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    public int checkForFiles(Command command) {
        scheduleFileDirectory.goThroughToCheckFile(command);
        return HttpStatus.OK_200;
    }

}