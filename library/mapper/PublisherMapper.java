package com.project.library.mapper;

import com.project.library.dto.PublisherDto;
import com.project.library.model.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto toDto(Publisher publisher) {
        return new PublisherDto(publisher.getId(), publisher.getName(), publisher.getAddress());
    }

    public Publisher toModel(PublisherDto publisherDto) {
        Publisher publisher = new Publisher();
        publisher.setId(publisherDto.id());
        publisher.setName(publisherDto.name());
        publisher.setAddress(publisherDto.address());
        return publisher;
    }
}





