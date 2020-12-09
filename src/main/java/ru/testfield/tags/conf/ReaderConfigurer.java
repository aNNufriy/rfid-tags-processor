package ru.testfield.tags.conf;

import com.clou.uhf.G3Lib.Enumeration.eAntennaNo;
import com.clou.uhf.G3Lib.Enumeration.eRF_Range;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderConfigurer {

    private final Map<String, Object> propertiesMap;

    public ReaderConfigurer() {
        this.propertiesMap = PropertiesYamlLoader.loadFromYml();
    }

    public String getClouAddress() {
        return validateAndReturn("clou_address", String.class);
    }

    public int getClouPort() {
        return validateAndReturn("clouPort", Integer.class);
    }

    public String getConnID() {
        return this.getClouAddress() + ":" + this.getClouPort();
    }

    public int getTagUpdateTime() {
        return validateAndReturn("tagUpdateTime", Integer.class) / 10;
    }

    public eRF_Range getFreqRange() {
        return eRF_Range.valueOf(validateAndReturn("frequency", String.class));
    }

    public Map<Integer, Integer> getAntsPower() {
        final List<?> antennas = validateAndReturn("antennas", List.class);
        Map<Integer, Integer> antsPower = new HashMap<>();
        for(Object antenna: antennas){
            if(antenna instanceof Map){
                final Integer number = (Integer) ((Map<String, Object>) antenna).get("number");
                final Integer powerPercent  = (Integer) ((Map<String, Object>) antenna).get("power_percent");

                final int power = (int) Math.round(powerPercent / 100.0 * 33);
                final Integer previous = antsPower.put(number, power);
                if(previous!=null){
                    throw new InconsistentConfigException("antenna number duplicated: " + number);
                }
            }
        }
        return antsPower;
    }

    public int getBasebandMode() {
        return (Integer) validateAndReturn("baseBandMode", Integer.class);
    }

    public int getBasebandSession() {
        return (Integer) validateAndReturn("baseBandSession", Integer.class);
    }

    public int getAnt() {
        int ant = 0;
        if (validateAndReturn("antenna1enabled", Boolean.class)) {
            ant += eAntennaNo._1.GetNum();
        }
        if (validateAndReturn("antenna2enabled", Boolean.class)) {
            ant += eAntennaNo._2.GetNum();
        }
        if (validateAndReturn("antenna3enabled", Boolean.class)) {
            ant += eAntennaNo._3.GetNum();
        }
        if (validateAndReturn("antenna4enabled", Boolean.class)) {
            ant += eAntennaNo._4.GetNum();
        }
        return ant;
    }

    public boolean shouldReadTids() {
        return validateAndReturn("readTid", Boolean.class);
    }

    public int getBasebandQValue() {
        return validateAndReturn("baseBandQValue", Integer.class);
    }

    public int getBasebandSearchType() {
        return validateAndReturn("baseBandSearchType", Integer.class);
    }

    public String getTfsRecieverUrl() {
        return validateAndReturn("tfsReceiverUrl", String.class);
    }

    public int getMaxRetry() {
        return validateAndReturn("maxRetry", Integer.class);
    }

    protected <T> T validateAndReturn(String propertyName, Class<T> clazz) {
        final Object value = propertiesMap.get(propertyName);
        if(value==null){
            throw new ExpectedParameterNotFoundException("No such parameter: "+propertyName);
        }
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        } else {
            throw new TypeMismatchException("Type missmatch. expected: " + clazz.getName() + ", got" + value.getClass().getName());
        }
    }

    public static class TypeMismatchException extends RuntimeException {
        public TypeMismatchException(String msg) {
            super(msg);
        }
    }

    public static class ExpectedParameterNotFoundException extends RuntimeException {
        public ExpectedParameterNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class InconsistentConfigException extends RuntimeException {
        public InconsistentConfigException(String msg) {
            super(msg);
        }
    }
}