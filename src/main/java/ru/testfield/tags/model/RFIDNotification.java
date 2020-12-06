package ru.testfield.tags.model;

import ru.testfield.tags.model.adapters.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name="Alien-RFID-Reader-Auto-Notification")
public interface RFIDNotification {

    @XmlElement(name = "UUID")
    UUID getUUID();

    @XmlElement(name = "ReaderName")
    String getReaderName();

    @XmlElement(name = "ReaderType")
    String getReaderType();

    @XmlElement(name = "IPAddress")
    String getIpAddress();

    @XmlElement(name = "CommandPort")
    int getCommandPort();

    @XmlElement(name = "MACAddress")
    String getMacAddress();

    @XmlElement(name = "Reason")
    default String getReason() {
        return "ACTIVE REQUEST";
    }

    @XmlElement(name = "StartTriggerLines")
    default String getStartTriggerLines(){
        return "0";
    }

    @XmlElement(name = "StopTriggerLines")
    default String getStopTriggerLines() {
        return "0";
    }

    @XmlElement(name = "Time")
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    LocalDateTime getTime();

    @XmlElement(name = "Alien-RFID-Tag")
    @XmlElementWrapper(name="Alien-RFID-Tag-List")
    List<RFIDTag> getRfidTags();

    default int getRfidTagsSize() {
        return getRfidTags()==null?0:getRfidTags().size();
    }
}
