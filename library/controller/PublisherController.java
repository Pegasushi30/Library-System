package com.project.library.controller;

import com.project.library.dto.PublisherDto;
import com.project.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDto> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public Optional<PublisherDto> getPublisherById(@PathVariable UUID id) {
        return publisherService.getPublisherById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public PublisherDto createPublisher(@RequestBody PublisherDto publisherDto) {
        return publisherService.createPublisher(publisherDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public PublisherDto updatePublisher(@PathVariable UUID id, @RequestBody PublisherDto publisherDto) {
        return publisherService.updatePublisher(id, publisherDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable UUID id) {
        publisherService.deletePublisher(id);
    }
}

