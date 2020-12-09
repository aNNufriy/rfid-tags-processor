package ru.testfield.tags.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.Map;

public class PropertiesYamlLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesYamlLoader.class);

    public static Map<String, Object> loadFromYml() {
        return loadFromYml(System.getProperty("user.dir") + "/properties.yml");
    }

    public static Map<String, Object> loadFromYml(String filename) {
        try (InputStream propertiesFileStream = new FileInputStream(new File(filename))) {
            final Map<String, Object> propertiesMap = loadFromYml(propertiesFileStream);
            if(propertiesMap==null){
                throw new UnableToLoadPropertiesFileException("Yaml unable to parse file");
            }else{
                return propertiesMap;
            }
        } catch (IOException e) {
            String msg = String.format("Unable to open properties file: %s", filename);
            logger.error(msg);
            throw new PropertiesFileNotFoundException(msg, e);
        }
    }

    public static Map<String, Object> loadFromYml(InputStream propertiesFileStream) {
        final Yaml yaml = new Yaml(new Constructor(ReaderConfigurer.class));
        return new Yaml().load(propertiesFileStream);
    }

    static class PropertiesFileNotFoundException extends RuntimeException {
        PropertiesFileNotFoundException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
    static class UnableToLoadPropertiesFileException extends RuntimeException {
        UnableToLoadPropertiesFileException(String msg) {
            super(msg);
        }
    }
}