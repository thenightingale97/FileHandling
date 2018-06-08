package filemanager.service;

import filemanager.model.Command;

public interface JobWriterService {

    void saveJobInformation(String clientName, Command command);

}
