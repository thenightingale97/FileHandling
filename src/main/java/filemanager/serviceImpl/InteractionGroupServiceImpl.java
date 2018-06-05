package filemanager.serviceImpl;

import filemanager.model.Interaction;
import filemanager.service.InteractionGroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InteractionGroupServiceImpl implements InteractionGroupService {
    @Override
    public void groupByClient(List<Interaction> interactions, HashMap<String, List<Interaction>> interactionsMap) {
        interactions.forEach((interaction -> {
            if (interactionsMap.containsKey(interaction.getClientName())) {
                interactionsMap.get(interaction.getClientName()).add(interaction);
            } else {
                List<Interaction> temporaryInteractionList = new ArrayList<>();
                temporaryInteractionList.add(interaction);
                interactionsMap.put(interaction.getClientName(), temporaryInteractionList);
            }
        }));
    }
}
