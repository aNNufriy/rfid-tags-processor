package ru.testfield.tags.service.consumer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class StatusHolderConsumer<T extends List<?>> implements Consumer<T> {

    private Consumer<T> consumer;

    private ZonedDateTime lastTagProcessedTime = ZonedDateTime.now(ZoneOffset.UTC);

    private long totalCount;

    public StatusHolderConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        lastTagProcessedTime = ZonedDateTime.now(ZoneOffset.UTC);
        consumer.accept(t);
        totalCount += t.size();
    }

    public Map<String,Object> getStatus() {
        return Map.of("totalCount",totalCount,"lastTagProcessedTime",lastTagProcessedTime);
    }
}
