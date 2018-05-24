package filemanager.serviceImpl;

import com.google.inject.Inject;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class WatchFileDirectory {

    private WatchService watchService;
    private Map<WatchKey, Path> watchKeys;
    private ExecutorService watchExecutor = Executors.newSingleThreadExecutor();

    @Inject
    private JsonReader reader;

    @Inject
    private JsonToXmlConverter converter;

    @Inject
    private XmlWriter writer;

    public WatchFileDirectory() throws IOException {
        this.watchKeys = new HashMap<>();
        this.watchService = FileSystems.getDefault().newWatchService();
    }

    public void startChangeTracking(Path path) {
        try {
            watchExecutor.execute(() -> {
                try {
                    this.registerAll(path);
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
            });
        } finally {
            watchExecutor.shutdown();
        }

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
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                File newFile = new File(file.toString());
                                JSONObject jsonObject = reader.readJson(new FileInputStream(newFile));
                                String client = reader.getClientFromJson(new FileInputStream(newFile));
                                String xml = converter.convert(jsonObject);
                                writer.writeXmlFile(xml, client);
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

    public ExecutorService getWatchExecutor() {
        return watchExecutor;
    }
}