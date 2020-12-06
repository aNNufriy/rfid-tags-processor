package ru.testfield.tags.service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testfield.tags.model.RFIDNotification;

import java.util.function.Consumer;

public class LogOnlyTagsConsumer implements Consumer<RFIDNotification> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void accept(RFIDNotification notification) {
        logger.info("Processed tags: {}, thread: {}",notification.getRfidTagsSize(),Thread.currentThread().getName());
    }
}
