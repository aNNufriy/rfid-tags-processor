package ru.testfield.tags.service;

import org.springframework.stereotype.Service;
import ru.testfield.tags.conf.ReaderConfigurer;
import javax.annotation.PostConstruct;

@Service
public class TagsReadingService {

    ReaderConfigurer readerConfigurer = new ReaderConfigurer();

    @PostConstruct
    public void postConstruct() {
        System.out.println(readerConfigurer.getClouPort());
    }

}
