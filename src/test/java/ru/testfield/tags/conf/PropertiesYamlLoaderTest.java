package ru.testfield.tags.conf;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PropertiesYamlLoaderTest {

    @Test
    public void loadFromYmlTest() throws IOException {
        final URL propertiesFileURL = this.getClass().getClassLoader().getResource("test.properties.yml");
        assertNotNull(propertiesFileURL);

        final Map<String, Object> loadFromFile = PropertiesYamlLoader
                .loadFromYml(propertiesFileURL.getFile());
        final Map<String, Object> loadFromStream = PropertiesYamlLoader
                .loadFromYml(propertiesFileURL.openStream());
        assertEquals(loadFromFile,loadFromStream);
        assertEquals(3,loadFromFile.size());
        assertEquals("localhost", loadFromFile.get("string_value"));
        assertEquals(9090, loadFromFile.get("integer_value"));
        assertEquals(4, ((List<?>)loadFromFile.get("list_value")).size());
    }

}
