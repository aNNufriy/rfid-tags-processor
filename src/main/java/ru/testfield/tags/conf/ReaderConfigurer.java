package ru.testfield.tags.conf;

import com.clou.uhf.G3Lib.Enumeration.eAntennaNo;
import com.clou.uhf.G3Lib.Enumeration.eRF_Range;

import java.util.HashMap;
import java.util.Map;

public class ReaderConfigurer {

    final Map<String, Object> propertiesMap;

    public ReaderConfigurer() {
        this.propertiesMap = TagsReceivingPropertiesYamlLoader.loadFromYml();
    }

    public String getClouAddress() {
        return propertiesMap.get("clouAddress").toString();
    }

    public int getClouPort() {
        return (Integer) validateAndReturn("clouPort", Integer.class);
    }

    public String getConnID() {
        return this.getClouAddress() + ":" + this.getClouPort();
    }

    public int getTagUpdateTime() {
        return (Integer) validateAndReturn("tagUpdateTime", Integer.class) / 10;
    }

    public eRF_Range getFreqRange() {
        return eRF_Range.valueOf((String) validateAndReturn("frequency", String.class));
    }

    public Map<Integer, Integer> getAntsPower() {
        Map<Integer, Integer> antsPower = new HashMap<>();
        antsPower.put(1, (Integer) validateAndReturn("antenna1power", Integer.class));
        antsPower.put(2, (Integer) validateAndReturn("antenna2power", Integer.class));
        antsPower.put(3, (Integer) validateAndReturn("antenna3power", Integer.class));
        antsPower.put(4, (Integer) validateAndReturn("antenna4power", Integer.class));
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
        if ((Boolean) validateAndReturn("antenna1enabled", Boolean.class)) {
            ant += eAntennaNo._1.GetNum();
        }
        if ((Boolean) validateAndReturn("antenna2enabled", Boolean.class)) {
            ant += eAntennaNo._2.GetNum();
        }
        if ((Boolean) validateAndReturn("antenna3enabled", Boolean.class)) {
            ant += eAntennaNo._3.GetNum();
        }
        if ((Boolean) validateAndReturn("antenna4enabled", Boolean.class)) {
            ant += eAntennaNo._4.GetNum();
        }
        return ant;
    }

    public boolean shouldReadTids() {
        return (Boolean) validateAndReturn("readTid", Boolean.class);
    }

    public int getBasebandQValue() {
        return (Integer) validateAndReturn("baseBandQValue", Integer.class);
    }

    public int getBasebandSearchType() {
        return (Integer) validateAndReturn("baseBandSearchType", Integer.class);
    }

    public String getTfsRecieverUrl() {
        return (String) validateAndReturn("tfsReceiverUrl", String.class);
    }

    public int getMaxRetry() {
        return (Integer) validateAndReturn("maxRetry", Integer.class);
    }

    protected Object validateAndReturn(String propertyName, Class<?> clazz) {
        final Object value = propertiesMap.get(propertyName);
        if(value==null){
            throw new ExpectedParameterNotFoundException("No such parameter: "+propertyName);
        }
        if (clazz.isInstance(value)) {
            return value;
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
}