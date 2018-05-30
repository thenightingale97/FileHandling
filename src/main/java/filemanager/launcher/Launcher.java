package filemanager.launcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import filemanager.binder.FileServiceBinderModule;
import filemanager.controller.FileHandlerApplication;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class Launcher extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        Injector guice = Guice.createInjector(new FileServiceBinderModule());
        FileHandlerApplication application = guice.getInstance(FileHandlerApplication.class);
        application.runProgram();
    }
}
