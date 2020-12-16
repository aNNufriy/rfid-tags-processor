package ru.testfield.tags.service;

import org.springframework.stereotype.Service;
import ru.testfield.tags.conf.ClouReaderConfigurer;
import ru.testfield.tags.service.consumer.LogOnlyListConsumer;
import ru.testfield.tags.service.consumer.StatusHolderConsumer;
import ru.testfield.tags.service.packer.Packer;
import ru.testfield.tags.service.packer.QueuePacker;
import ru.testfield.tags.service.processor.SchedulerProcessor;
import ru.testfield.tags.service.processor.TagsProcessor;
import ru.testfield.tags.service.supplier.MockTagsReader;
import ru.testfield.tags.service.supplier.TagsReader;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TagsReadingService {

    final ClouReaderConfigurer clouReaderConfigurer = new ClouReaderConfigurer();

    final Packer<String> packer = new QueuePacker<>();
    final TagsReader<String> tagsReader = new MockTagsReader();
    final StatusHolderConsumer<List<String>> consumer = new StatusHolderConsumer<>(new LogOnlyListConsumer<>());

    final TagsProcessor processor = new SchedulerProcessor<>(packer, consumer,1, TimeUnit.SECONDS);

    @PostConstruct
    public void postConstruct() {
        tagsReader.acceptPacker(packer);
        processor.startProcessing();
    }

    public Map<String,Object> getStatus(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("isScannerOnline",isScannerOnline());
        map.put("consumptionStatus",consumer.getStatus());
        map.put("serverTimeUTC", ZonedDateTime.now(ZoneOffset.UTC));
        return map;
    }

    private boolean isScannerOnline(){
        return tagsReader.isOnline();
    }
}