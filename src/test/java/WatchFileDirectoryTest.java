
import filemanager.directorytracker.WatchFileDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WatchFileDirectoryTest {

    WatchFileDirectory watchFileDirectory;

    @After
    public void tearDown() throws Exception {
        watchFileDirectory = null;
    }

    @Before
    public void setUp() throws Exception {
        watchFileDirectory = new WatchFileDirectory();
    }

    @Test
    public void matchPatternTest() throws IOException {
        boolean expected = true;
        boolean actual = watchFileDirectory.matchPattern("/dsa/TestValue");
        assertEquals(expected, actual);
    }
}
