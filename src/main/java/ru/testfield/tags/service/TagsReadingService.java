package ru.testfield.tags.service;

import org.springframework.stereotype.Service;
import ru.testfield.tags.conf.ReaderConfigurer;
import ru.testfield.tags.service.consumer.LogOnlyListConsumer;
import ru.testfield.tags.service.packer.Packer;
import ru.testfield.tags.service.packer.QueuePacker;
import ru.testfield.tags.service.processor.SchedulerProcessor;
import ru.testfield.tags.service.processor.TagsProcessor;
import ru.testfield.tags.service.supplier.MockTagsReader;
import ru.testfield.tags.service.supplier.TagsReader;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class TagsReadingService {

    ReaderConfigurer readerConfigurer = new ReaderConfigurer();

    Packer<String> packer = new QueuePacker<>();
    TagsReader<String> tagsReader = new MockTagsReader();
    TagsProcessor processor = new SchedulerProcessor<>(packer, new LogOnlyListConsumer<>(),1, TimeUnit.SECONDS);

    @PostConstruct
    public void postConstruct() {
        tagsReader.acceptPacker(packer);
        processor.startProcessing();
        System.out.println(readerConfigurer.getClouPort());
        System.out.println(readerConfigurer.getAntsPower());
    }
}