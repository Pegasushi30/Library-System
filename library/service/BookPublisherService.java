package com.project.library.service;

import com.project.library.dto.BookPublisherDto;
import java.util.List;
import java.util.UUID;

public interface BookPublisherService {
    List<BookPublisherDto> getAllBookPublishers();
    BookPublisherDto getBookPublisherById(UUID id);
    List<BookPublisherDto> getBookPublishersByBook(Integer isbn);
}






