package ru.testfield.tags.service.packer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dropping10ThCapacityOnOverflowPacker<V> extends Packer<V> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean addValue(V value) {
        if (blockingQueue.size() == QUEUE_CAPACITY) {
            this.getPack(QUEUE_CAPACITY / 10);
            logger.error("dropped 1/10 of queue due to an overflow: {}", QUEUE_CAPACITY);
        }
        return super.addValue(value);
    }

}
