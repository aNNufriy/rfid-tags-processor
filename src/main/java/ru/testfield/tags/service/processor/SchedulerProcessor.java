package ru.testfield.tags.service.processor;

import ru.testfield.tags.service.packer.Packer;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SchedulerProcessor<T> implements TagsProcessor {

    private final Packer<T> packer;

    private final ScheduledExecutorService scheduledExecutorService;

    private final Consumer<? super List<T>> consumer;

    private final long interval;

    private final TimeUnit timeUnit;

    public SchedulerProcessor(Packer<T> packer, Consumer<? super List<T>> consumer,
                              long interval, TimeUnit timeUnit) {
        this.packer = packer;
        this.consumer = consumer;
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void startProcessing() {
        scheduledExecutorService.scheduleAtFixedRate(this::consumePack,0,interval,timeUnit);
    }

    @Override
    public void close() {
        scheduledExecutorService.shutdown();
    }

    private void consumePack() {
        packer.getPack().ifPresent(consumer);
    }
}
