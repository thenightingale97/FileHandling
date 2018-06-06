import com.google.inject.Inject;
import filemanager.model.Interaction;
import filemanager.service.InteractionGroupService;
import filemanager.serviceImpl.InteractionGroupServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InteractionGroupServiceTest {

    @Inject
    InteractionGroupService groupService;

    @After
    public void tearDown() {
        groupService = null;
    }

    @Before
    public void setUp() {
        groupService = new InteractionGroupServiceImpl();
    }

    @Test
    public void groupByClientValidInputTest() {
        List<Interaction> testValue = new ArrayList<>();
        Interaction interaction1 = new Interaction();
        interaction1.setClientName("Test1");
        Interaction interaction2 = new Interaction();
        interaction2.setClientName("Test2");
        Interaction interaction3 = new Interaction();
        interaction3.setClientName("Test2");
        testValue.add(interaction1);
        testValue.add(interaction2);
        testValue.add(interaction3);

        HashMap<String, List<Interaction>> hashMap = new HashMap<>();
        groupService.groupByClient(testValue, hashMap);

        assertEquals(2, hashMap.keySet().size());
    }

    @Test
    public void groupByClientEmptyList() {
        List<Interaction> testValue = new ArrayList<>();
        HashMap<String, List<Interaction>> hashMap = new HashMap<>();
        groupService.groupByClient(testValue, hashMap);

        assertEquals(0, hashMap.size());
    }

}
