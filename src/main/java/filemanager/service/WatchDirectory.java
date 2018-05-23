package filemanager.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface WatchDirectory {
    void startChangeTracking(Path path) throws IOException;
}
