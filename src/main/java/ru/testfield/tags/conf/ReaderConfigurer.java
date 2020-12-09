package ru.testfield.tags.conf;

import ru.testfield.tags.conf.holders.Antenna;
import ru.testfield.tags.conf.holders.ReaderProperties;

import java.util.HashMap;
import java.util.Map;

public class ReaderConfigurer {

    private final ReaderProperties properties;

    public ReaderConfigurer() {
        this.properties = PropertiesYamlLoader.loadFromYml("properties.yml");
    }

    public String getClouAddress() {
        return properties.getClouAddress();
    }

    public int getClouPort(){
        return properties.getClouPort();
    }

    public Map<Integer, Integer> getAntennasPower(){
        Map<Integer,Integer> antsPower = new HashMap<>();
        for(Antenna antenna: properties.getAntennas()){
            final Integer previous = antsPower.put(antenna.getNumber(), percentsToPowerLevel(antenna.getPowerPercent()));
            if(previous!=null){
                String msg = "Multiple configuration sets for antenna: "+antenna.getNumber();
                throw new AntennaNumberDuplicatedException(msg);
            }
        }
        return antsPower;
    }

    private int percentsToPowerLevel(int powerPercent) {
        return (int) Math.round(powerPercent/100.0*33);
    }

    static class AntennaNumberDuplicatedException extends RuntimeException {
        AntennaNumberDuplicatedException(String msg) {
            super(msg);
        }
    }
}