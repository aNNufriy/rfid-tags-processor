package ru.testfield.tags.conf.holders;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReaderProperties {
    private String clouAddress;
    private int clouPort;
    private Antenna[] antennas;
}