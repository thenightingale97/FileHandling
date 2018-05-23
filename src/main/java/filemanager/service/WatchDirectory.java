package filemanager.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface WatchDirectory {
    void startChangeTracking() throws IOException;
}
