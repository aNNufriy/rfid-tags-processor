package ru.testfield.tags.service.supplier;

import ru.testfield.tags.service.packer.Packer;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockTagsReader implements TagsReader<String> {
    private final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void acceptPacker(Packer<String> packer) {
        Runnable mockProducer = () -> packer.addValue(UUID.randomUUID().toString());
        es.scheduleAtFixedRate(mockProducer,0,100, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void close() {
        es.shutdown();
    }
}
