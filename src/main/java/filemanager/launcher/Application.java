package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.service.WatchDirectory;
import filemanager.serviceImpl.WatchDirectoryImpl;

import java.io.IOException;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new FileServiceBinderModule());
        WatchDirectory watchDirectory = injector.getInstance(WatchDirectoryImpl.class);
        try {
            watchDirectory.startChangeTracking(Paths.get("/home/ykato/Documents/bvpixel"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

