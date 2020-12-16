package ru.testfield.tags.service.supplier;

import ru.testfield.tags.service.packer.Packer;

public interface TagsReader<T> extends AutoCloseable {
    void acceptPacker(Packer<T> packer);
    boolean isOnline();
}
