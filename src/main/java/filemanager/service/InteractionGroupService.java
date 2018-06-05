package filemanager.service;

import filemanager.model.Interaction;

import java.util.HashMap;
import java.util.List;

public interface InteractionGroupService {
    void groupByClient(List<Interaction> interactions, HashMap<String, List<Interaction>> interactionsMap);
}
