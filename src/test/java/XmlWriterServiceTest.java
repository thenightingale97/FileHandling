import com.google.inject.Inject;
import filemanager.service.XmlWriteService;
import filemanager.service.impl.XmlWriteServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class XmlWriterServiceTest {

    @Inject
    XmlWriteService writeService;

    @After
    public void tearDown() {
        writeService = null;
    }

    @Before
    public void setUp() {
        writeService = new XmlWriteServiceImpl();
    }

    @Test
    public void generateFileNameTest() throws FileAlreadyExistsException {
        String expected = "test/test/ppe/inbox/bvpixel-2018101512.xml";
        String actual = writeService.generateFileName(LocalDateTime.of(2018, 10, 15, 12, 0), "test/test");
        assertEquals(expected, actual);
    }
    
}
