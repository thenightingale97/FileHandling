package filemanager.serviceImpl;

import com.google.inject.Inject;
import filemanager.service.JsonReader;
import filemanager.service.JsonToXmlConverter;
import filemanager.service.XmlWriter;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class WatchFileDirectory {

    private WatchService watchService;
    private Map<WatchKey, Path> watchKeys;
    private ExecutorService watchExecutor = Executors.newSingleThreadExecutor();
    private String outputPath;
    private String fileNamePattern;

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
                        .filter(file -> Files.isRegularFile(file) && matchPattern(file))
                        .forEach(file -> {
                            try {
                                JSONObject jsonObject = reader.readJson(new FileInputStream(String.valueOf(file)));
                                String client = reader.getClientFromJson(new FileInputStream(String.valueOf(file)));
                                outputPath += client;
                                String xml = converter.convert(jsonObject);
                                writer.writeXmlFile(xml, outputPath);
                                outputPath = "";
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

    private boolean matchPattern(Path file) {
        String fileName = file.toString();
        return fileName.substring(fileName.lastIndexOf("/") + 1).contains(fileNamePattern);
    }

    public ExecutorService getWatchExecutor() {
        return watchExecutor;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }
}
