package com.project.library.mapper;

import com.project.library.dto.BookAuthorDto;
import com.project.library.model.BookAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorMapper {

    private final AuthorMapper authorMapper;

    @Autowired
    public BookAuthorMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public BookAuthorDto toDto(BookAuthor bookAuthor) {
        return new BookAuthorDto(
                bookAuthor.getId(), // UUID kullanılıyor
                bookAuthor.getBook().getIsbn(), // Eğer ISBN hala Integer ise bu kısım Integer kalabilir
                authorMapper.toDto(bookAuthor.getAuthor())
        );
    }

    public BookAuthor toModel(BookAuthorDto dto) {
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setId(dto.id()); // UUID olarak ayarlandı
        return bookAuthor;
    }
}



