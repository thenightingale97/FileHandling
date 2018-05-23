package filemanager.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.service.ConfigurationService;
import filemanager.serviceImpl.WatchFileDirectory;

import java.nio.file.Paths;
import java.util.Scanner;

public class Application implements ConfigurationService {

    private static final Scanner sc = new Scanner(System.in);
    private static final String ROOT_FOLDER = "/home/ykato/Documents/bvpixel/";

    private String environment;
    private String rootFolder;
    private String outputPath;
    private String fileNamePattern;
    private WatchFileDirectory watchDirectory;

    public Application(String environment) {
        this.environment = environment;
    }

    @Override
    public void changeEnvironment() {
        System.out.println("Enter environment you need: ");
        environment = sc.next();
        if (watchDirectory.getWatchExecutor() != null) {
            watchDirectory.getWatchExecutor().shutdown();
            runProgram();
        }
    }

    @Override
    public void changeRoot() {

    }

    @Override
    public void changeFileNamePattern() {

    }

    @Override
    public void changeFileOutput() {

    }

    public void runProgram() {
        Injector injector = Guice.createInjector(new FileServiceBinderModule());
        watchDirectory = injector.getInstance(WatchFileDirectory.class);
        watchDirectory.startChangeTracking(Paths.get(ROOT_FOLDER + environment));
    }


}
