package ru.testfield.tags.model;

import lombok.Getter;

@Getter
public class ReaderInfo {
    private String readerName;

    private String ipAddress;

    private String readerType;

    private int commandPort;

    private String macAddress;
}
