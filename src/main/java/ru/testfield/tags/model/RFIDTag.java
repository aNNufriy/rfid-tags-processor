package ru.testfield.tags.model;

import ru.testfield.tags.model.adapters.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.UUID;

public interface RFIDTag {

    @XmlElement(name = "UUID")
    UUID getUUID();

    @XmlElement(name = "TagID")
    String getTid();

    @XmlElement(name = "EPC")
    String getEpc();

    @XmlElement(name = "Antenna")
    int getAntenna();

    @XmlElement(name = "ReadCount")
    default int getReadCount(){
        return 1;
    }

    @XmlElement(name = "RSSI")
    int getRSSI();

    @XmlElement(name = "DiscoveryTime")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    LocalDateTime discoveryTime = LocalDateTime.now();

    @XmlElement(name = "LastSeenTime")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    LocalDateTime lastSeenTime = LocalDateTime.now();

}