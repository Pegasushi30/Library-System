package com.project.library.mapper;

import com.project.library.dto.BookPublisherDto;
import com.project.library.model.BookPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookPublisherMapper {

    private final PublisherMapper publisherMapper;

    @Autowired
    public BookPublisherMapper(PublisherMapper publisherMapper) {
        this.publisherMapper = publisherMapper;
    }

    public BookPublisherDto toDto(BookPublisher bookPublisher) {
        return new BookPublisherDto(
                bookPublisher.getId(),
                bookPublisher.getBook().getIsbn(),
                publisherMapper.toDto(bookPublisher.getPublisher())
        );
    }

    public BookPublisher toModel(BookPublisherDto dto) {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setId(dto.id());
        return bookPublisher;
    }
}



