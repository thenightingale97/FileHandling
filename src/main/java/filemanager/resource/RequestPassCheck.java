package filemanager.resource;

import filemanager.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ForbiddenException;

public class RequestPassCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPassCheck.class);

    void checkPass(Command command) {
        String key = command.getPassKey();
        String client = command.getClient();
        if (client.isEmpty()) {
            client = "all";
        }
        if (key == null || key.isEmpty()) {
            throw new ForbiddenException();
        } else {
            LOGGER.info(key.hashCode() + " has been authorized to run job: \n" +
                    "date: " + command.getDate() + "\n" +
                    "client: " + client);
        }
    }

}
