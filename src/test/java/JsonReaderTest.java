import com.google.inject.Inject;
import filemanager.service.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
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


    @Test(expected = JSONException.class)
    public void testInvalidJsonInput() throws IOException {
        String testValue = "wrongJson";
        reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
    }

    @Test
    public void testValidStreamReading() throws IOException {
        JSONObject actual;
        JSONObject expected = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
        String testValue = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
        actual = reader.readJson(new ByteArrayInputStream(testValue.getBytes()));
        assertEquals(expected, actual);
    }
}
