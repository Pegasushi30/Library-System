package com.project.library.service.impl;

import com.project.library.dto.PublisherDto;
import com.project.library.exception.PublisherNotFoundException;
import com.project.library.mapper.PublisherMapper;
import com.project.library.model.Publisher;
import com.project.library.repository.PublisherRepository;
import com.project.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    @Override
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream().map(publisherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PublisherDto> getPublisherById(UUID id) {
        return publisherRepository.findById(id).map(publisherMapper::toDto);
    }

    @Override
    public PublisherDto createPublisher(PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.toModel(publisherDto);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(savedPublisher);
    }

    @Override
    public PublisherDto updatePublisher(UUID id, PublisherDto publisherDto) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));
        publisher.setName(publisherDto.name());
        publisher.setAddress(publisherDto.address());
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(updatedPublisher);
    }

    @Override
    public void deletePublisher(UUID id) {
        publisherRepository.deleteById(id);
    }
}
