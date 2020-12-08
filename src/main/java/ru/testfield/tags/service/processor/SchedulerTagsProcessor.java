package ru.testfield.tags.service.processor;

import com.clou.uhf.G3Lib.Models.Tag_Model;
import ru.testfield.tags.model.ClouRFIDNotification;
import ru.testfield.tags.model.ClouRFIDTag;
import ru.testfield.tags.model.RFIDNotification;
import ru.testfield.tags.model.RFIDTag;
import ru.testfield.tags.service.packer.Packer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SchedulerTagsProcessor implements TagsProcessor {

    private final Packer<Tag_Model> packer;

    private final ScheduledExecutorService scheduledExecutorService;

    private final Consumer<RFIDNotification> consumer;

    private final long interval;

    private final TimeUnit timeUnit;

    public SchedulerTagsProcessor(Packer<Tag_Model> packer, Consumer<RFIDNotification> consumer,
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
        packer.getPack().ifPresent(tagModels -> {
            RFIDNotification rfidNotification = getRfidNotification(tagModels);
            consumer.accept(rfidNotification);
        });
    }

    private RFIDNotification getRfidNotification(List<Tag_Model> tagModels) {
        List<RFIDTag> rfidTags = tagModels.stream()
                .map(ClouRFIDTag::new)
                .collect(Collectors.toList());
        return new ClouRFIDNotification(null, rfidTags);
    }
}
