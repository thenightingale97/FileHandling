import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.inject.Inject;
import filemanager.model.Interaction;
import filemanager.service.JsonReadService;
import filemanager.serviceImpl.JsonReadServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonReadServiceTest {

    @Inject
    JsonReadService reader;

    @After
    public void tearDown() {
        reader = null;
    }

    @Before
    public void setUp() {
        reader = new JsonReadServiceImpl();
    }

    @Test(expected = JsonParseException.class)
    public void readJsonInvalidJsonInputTest() throws IOException {
        String testValue = "wrongJson";
        reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void readJsonNotAppropriateDataTest() throws IOException {
        Interaction expected = new Interaction();
        expected.setClientName("Test");
        String testValue = "{\"clientTest\":\"Test\",\"DtTest\":\"\",\"emailTest\":\"\",\"userId\":\"\"}";
        List<Interaction> actual = new ArrayList<>(reader.readJson(new ByteArrayInputStream(testValue.getBytes())));
    }

    @Test
    public void readJsonValidStreamReadingTest() throws IOException {
        List<Interaction> expected = new ArrayList<>();
        Interaction interaction1 = new Interaction();
        interaction1.setClientName("Test1");
        Interaction interaction2 = new Interaction();
        interaction2.setClientName("Test2");
        Interaction interaction3 = new Interaction();
        interaction3.setClientName("Test3");
        expected.add(interaction1);
        expected.add(interaction2);
        expected.add(interaction3);
        String testValue = "{\"client\":\"Test1\",\"Dt\":\"\"," +
                "\"email\":\"\",\"userId\":\"\"}\n{\"client\":\"Test2\",\"Dt\":" +
                "\"\",\"email\":\"\",\"userId\":\"\"}\n{\"client\":" +
                "\"Test3\",\"Dt\":\"\",\"email\":\"\",\"userId\":\"\"}";
        List<Interaction> actual = new ArrayList<>(reader.readJson(new ByteArrayInputStream(testValue.getBytes())));
        assertEquals(expected.get(1).getClientName(), actual.get(1).getClientName());
    }

    @Test
    public void readJsonProblemInteractionTest() throws IOException {
        List<Interaction> expected = new ArrayList<>();
        Interaction interaction1 = new Interaction();
        interaction1.setClientName("Test1");
        Interaction interaction2 = new Interaction();
        interaction2.setClientName("Test2");
        Interaction interaction3 = new Interaction();
        interaction3.setClientName("Test3");
        expected.add(interaction1);
        expected.add(interaction2);
        expected.add(interaction3);
        String testValue = "{\"badField\":\"Test1\",\"Dt\":\"\",\"email\":\"\",\"userId\":\"\"}\n" +
                "{\"client\":\"Test2\",\"Dt\":\"\",\"email\":\"\",\"userId\":\"\"}\n" +
                "{\"client\":\"Test3\",\"Dt\":\"\",\"email\":\"\",\"userId\":\"\"}";
        List<Interaction> actual = new ArrayList<>(reader.readJson(new ByteArrayInputStream(testValue.getBytes())));
        assertEquals(expected.get(1).getClientName(), actual.get(0).getClientName());
    }

}
