package com.project.library.service;

import com.project.library.dto.PublisherDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublisherService {
    List<PublisherDto> getAllPublishers();
    Optional<PublisherDto> getPublisherById(UUID id);
    PublisherDto createPublisher(PublisherDto publisherDto);
    PublisherDto updatePublisher(UUID id, PublisherDto publisherDto);
    void deletePublisher(UUID id);
}

