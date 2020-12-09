package ru.testfield.tags.conf;

import org.junit.jupiter.api.Test;
import ru.testfield.tags.conf.holders.ReaderProperties;

import java.net.URL;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class PropertiesYamlLoaderTest {

    @Test
    public void loadFromYmlTest() {
        final URL propertiesFileURL = this.getClass().getClassLoader().getResource("test.properties.yml");
        assertNotNull(propertiesFileURL);
        final ReaderProperties propertiesFromFile = PropertiesYamlLoader.loadFromYml(propertiesFileURL.getFile());
        assertEquals("127.0.0.1", propertiesFromFile.getClouAddress());
        assertEquals(9090, propertiesFromFile.getClouPort());
        assertEquals("[antenna:[1,true,100], antenna:[2,true,100], antenna:[3,true,100], antenna:[4,true,100]]",
                Arrays.toString(propertiesFromFile.getAntennas()));
    }

}
