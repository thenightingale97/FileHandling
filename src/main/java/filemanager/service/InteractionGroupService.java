package filemanager.service;

import filemanager.model.Interaction;

import java.util.HashMap;
import java.util.List;

public interface InteractionGroupService {
    HashMap<String, List<Interaction>> groupByClient(List<Interaction> interactions);
}
