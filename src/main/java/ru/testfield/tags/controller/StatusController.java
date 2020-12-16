package ru.testfield.tags.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testfield.tags.service.TagsReadingService;

import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    final TagsReadingService tagsReadingService;

    public StatusController(TagsReadingService tagsReadingService) {
        this.tagsReadingService = tagsReadingService;
    }

    @GetMapping({"/",""})
    public Map<String,Object> getStatus(){
        return tagsReadingService.getStatus();
    }
}