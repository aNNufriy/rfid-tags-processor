package ru.testfield.tags.service.processor;

public interface TagsProcessor extends AutoCloseable {
    void startProcessing();
}