package filemanager.directorytracker;

import com.google.inject.Inject;
import filemanager.model.Command;
import filemanager.service.ConverterFromJsonToXmlService;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class WatchFileDirectory extends TrackerFileDirectory {

    private WatchService watchService;
    private Map<WatchKey, Path> watchKeys;

    private ConverterFromJsonToXmlService converter;

    @Inject
    public WatchFileDirectory(ConverterFromJsonToXmlService converter) {
        this.converter = converter;
        this.watchKeys = new HashMap<>();
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goThroughToCheckFile() {
        try {
            registerAll(Paths.get(getRootFolder() + getEnvironment()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (; ; ) {
            WatchKey key;
            try {
                key = watchService.take();
            } catch (InterruptedException ex) {
                return;
            }

            Path dir = watchKeys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            watchForEvents(key, dir);
            boolean valid = key.reset();
            if (!valid) {
                watchKeys.remove(key);
                if (watchKeys.isEmpty()) {
                    break;
                }
            }
        }
    }

    @Override
    public void goThroughToCheckFile(LocalDateTime time) {

    }

    @Override
    public void goThroughToCheckFile(Command command) {

    }

    private void register(Path dir) throws IOException {
        WatchKey watchKey = dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        watchKeys.put(watchKey, dir);
    }

    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void watchForEvents(WatchKey key, Path dir) {
        for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
            Path name = watchEvent.context();
            Path child = dir.resolve(name);
            System.out.format("%s: %s\n", event.kind().name(), child);
            try (Stream<Path> paths = Files.walk(child)) {
                paths
                        .filter(file -> Files.isRegularFile(file) && matchPattern(file.toString()))
                        .forEach(file -> {
                            try {
                                converter.readJsonConvertToXmlAndWrite(file, getOutputPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                    registerAll(child);
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }

    public boolean matchPattern(String path) {
        return path.substring(path.lastIndexOf("/") + 1).contains(getFileNamePattern());
    }

}