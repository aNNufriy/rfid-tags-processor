package ru.testfield.tags.service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class LogOnlyListConsumer<V,T extends List<V>> implements Consumer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void accept(T pack) {
        logger.info("Processed values: {}", pack.size());
    }
}
