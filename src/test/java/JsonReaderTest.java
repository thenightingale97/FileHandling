import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.inject.Inject;
import filemanager.model.Interaction;
import filemanager.service.JsonReader;
import filemanager.serviceImpl.JsonReaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonReaderTest {

    @Inject
    JsonReader reader;

    @After
    public void tearDown() {
        reader = null;
    }

    @Before
    public void setUp() {
        reader = new JsonReaderImpl();
    }

    @Test(expected = JsonParseException.class)
    public void readJsonInvalidJsonInputTest() throws IOException {
        String testValue = "wrongJson";
        reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void readJsonNotAppropriateDataTest() throws IOException {
        Interaction actual;
        Interaction expected = new Interaction();
        expected.setClientName("Test");
        String testValue = "{\"clientTest\":\"Test\",\"DtTest\":\"\",\"emailTest\":\"\",\"userId\":\"\"}";
        actual = reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
        assertEquals(expected.getClientName(), actual.getClientName());
    }

    @Test
    public void readJsonValidStreamReadingTest() throws IOException {
        Interaction actual;
        Interaction expected = new Interaction();
        expected.setClientName("Test");
        String testValue = "{\"client\":\"Test\",\"Dt\":\"\",\"email\":\"\",\"userId\":\"\"}";
        actual = reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
        assertEquals(expected.getClientName(), actual.getClientName());
    }

}
