package ru.testfield.tags.conf.holders;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Antenna {
    int number;
    boolean enabled;
    int powerPercent;

    @Override
    public String toString(){
        return String.format("antenna:[%d,%b,%d]",number,enabled,powerPercent);
    }
}
