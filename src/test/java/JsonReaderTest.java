import com.google.inject.Inject;
import filemanager.service.JsonReader;
import filemanager.serviceImpl.JsonReaderImpl;
import org.json.JSONException;
import org.json.JSONObject;
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
    public void tearDown() throws Exception {
        reader = null;
    }

    @Before
    public void setUp() throws Exception {
        reader = new JsonReaderImpl();
    }

    @Test(expected = JSONException.class)
    public void readJsonInvalidJsonInputTest() throws IOException {
        String testValue = "wrongJson";
        reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
    }

    @Test
    public void readJsonValidStreamReadingTest() throws IOException {
        JSONObject actual;
        JSONObject expected = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
        String testValue = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
        actual = reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getClientFromJsonTest() throws IOException {
        String testValue = "{\"phonetype\":\"N95\",\"cat\":\"WP\", \"client\":\"Carl\"}";
        String expected = "Carl";
        assertEquals(expected, reader.getClientFromJson(new ByteArrayInputStream(testValue.getBytes())));
    }
}
