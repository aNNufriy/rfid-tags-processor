package ru.testfield.tags.conf;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.testfield.tags.conf.dto.Antenna;
import ru.testfield.tags.conf.dto.ReaderProperties;

import java.io.*;

public class PropertiesYamlLoader {

    public static ReaderProperties loadFromYml(String filename) {
        try (InputStream propertiesFileStream = new FileInputStream(new File(filename))) {
            final ReaderProperties properties = loadFromYml(propertiesFileStream);
            if(properties==null){
                throw new UnableToLoadPropertiesFileException("Yaml unable to parse file: "+filename);
            }else{
                return properties;
            }
        } catch (IOException e) {
            String msg = String.format("Unable to read properties file: %s", filename);
            throw new PropertiesFileNotFoundException(msg, e);
        }
    }

    private static ReaderProperties loadFromYml(InputStream propertiesFileStream) {
        Constructor constructor = new Constructor(ReaderProperties.class);
        TypeDescription customTypeDescription = new TypeDescription(ReaderProperties.class);
        customTypeDescription.addPropertyParameters("antennas", Antenna.class);
        constructor.addTypeDescription(customTypeDescription);

        Yaml yaml = new Yaml(constructor);
        return yaml.load(propertiesFileStream);
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