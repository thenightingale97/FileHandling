package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.controller.Application;

import java.time.LocalDateTime;

public class Launcher {
    public static void main(String[] args) {
        Injector guice = Guice.createInjector(new FileServiceBinderModule());
        Application application = guice.getInstance(Application.class);
        LocalDateTime time = LocalDateTime.of(2018, 5, 23, 6, 0);
        application.runProgram(LocalDateTime.now());
    }

}