package filemanager.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.serviceImpl.WatchFileDirectory;


import java.nio.file.Paths;

public class Application {

    private String environment;
    private String rootFolder;
    private String outputPath;
    private String fileNamePattern;
    private WatchFileDirectory watchDirectory;

    public Application(String environment, String rootFolder, String outputPath) {
        this.outputPath = outputPath;
        this.environment = environment;
        this.rootFolder = rootFolder;
    }


    public void runProgram() {
        Injector injector = Guice.createInjector(new FileServiceBinderModule());
        watchDirectory = injector.getInstance(WatchFileDirectory.class);
        watchDirectory.setOutputPath(outputPath);
        watchDirectory.startChangeTracking(Paths.get(rootFolder + environment));
    }
}
