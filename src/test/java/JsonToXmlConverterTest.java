

import com.google.inject.Inject;
import filemanager.service.JsonToXmlConverter;
import filemanager.serviceImpl.JsonToXmlConverterImpl;
import junit.framework.Assert;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonToXmlConverterTest {

    @Inject
    JsonToXmlConverter converter;

    @After
    public void tearDown() throws Exception {
        converter = null;
    }

    @Before
    public void setUp() throws Exception {
        converter = new JsonToXmlConverterImpl();
    }

    @Test
    public void convertTest() throws IOException {
        String expected = "<title>testJson</title>";
        JSONObject testValue = new JSONObject("{\"title\":\"testJson\"}");
        assertEquals(expected, converter.convert(testValue));
    }
}
