package filemanager.launcher;

import filemanager.serviceImpl.WatchDirectoryImpl;

import java.io.IOException;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {

        try {
            new WatchDirectoryImpl(Paths.get("/home/ykato/Documents/bvpixel")).startChangeTracking();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
