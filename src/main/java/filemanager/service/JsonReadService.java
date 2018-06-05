package filemanager.service;

import filemanager.model.Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public interface JsonReadService {
    List<Interaction> readJson(InputStream stream, List<Interaction> interactions) throws IOException;
}
