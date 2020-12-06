package ru.testfield.tags.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClouRFIDNotification implements RFIDNotification {

    private final UUID uuid;

    private final ReaderInfo readerInfo;

    private final LocalDateTime time = LocalDateTime.now();

    private final List<RFIDTag> rfidTags;

    public ClouRFIDNotification(ReaderInfo readerInfo, List<RFIDTag> rfidTags) {
        this.uuid = UUID.randomUUID();
        this.readerInfo = readerInfo;
        this.rfidTags = rfidTags;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    @Override
    public String getReaderName() {
        return this.readerInfo.getReaderName();
    }

    @Override
    public String getReaderType() {
        return this.readerInfo.getReaderType();
    }

    @Override
    public String getIpAddress() {
        return this.readerInfo.getIpAddress();
    }

    @Override
    public int getCommandPort() {
        return this.readerInfo.getCommandPort();
    }

    @Override
    public String getMacAddress() {
        return this.readerInfo.getMacAddress();
    }

    @Override
    public LocalDateTime getTime() {
        return null;
    }

    @Override
    public List<RFIDTag> getRfidTags() {
        return new ArrayList<>(this.rfidTags);
    }

    @Override
    public String toString() {
        return "RFIDNotification{" +
                "rfidTagsNumber=" + getRfidTagsSize() +
                ", readerName='" + getReaderName() + '\'' +
                ", readerType='" + getReaderType() + '\'' +
                ", ipAddress='" + getIpAddress() + '\'' +
                ", commandPort='" + getCommandPort() + '\'' +
                ", macAddress='" + getMacAddress() + '\'' +
                ", reason='" + getReason() + '\'' +
                ", startTriggerLines='" + getStartTriggerLines() + '\'' +
                ", stopTriggerLines='" + getStopTriggerLines() + '\'' +
                ", time=" + time +
                '}';
    }
}
