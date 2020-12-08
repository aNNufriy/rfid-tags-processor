package ru.testfield.tags.model;

import com.clou.uhf.G3Lib.Models.Tag_Model;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClouRFIDTag implements RFIDTag {

    public ClouRFIDTag(Tag_Model tagModel) {
        this.uuid = UUID.randomUUID();
        this.tagModel = tagModel;
    }

    private final UUID uuid;

    private final Tag_Model tagModel;

    public UUID getUUID(){
        return this.uuid;
    }

    public String getTid() {
        return tagModel._TID;
    }

    public String getEpc() {
        return tagModel._EPC;
    }

    public int getAntenna(){
        return tagModel._ANT_NUM;
    }

    public int getReadCount(){
        return 1;
    }

    public int getRSSI(){
        return tagModel._RSSI;
    }

    private final LocalDateTime discoveryTime = LocalDateTime.now();

    private final LocalDateTime lastSeenTime = LocalDateTime.now();

    @Override
    public String toString() {
        return "RFIDTag{" +
                "tid='" + getTid() + '\'' +
                ", epc='" + getEpc() + '\'' +
                ", discoveryTime=" + discoveryTime +
                ", lastSeenTime=" + lastSeenTime +
                ", antenna=" + getAntenna() +
                ", readCount=" + getReadCount() +
                '}';
    }
}