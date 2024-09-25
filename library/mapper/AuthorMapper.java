package com.project.library.mapper;

import com.project.library.dto.AuthorDto;
import com.project.library.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getId(),  // UUID olarak alınıyor
                author.getName(),
                author.getSurname(),
                author.getBirthdate()
        );
    }

    public Author toModel(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.id());  // UUID olarak set ediliyor
        author.setName(dto.name());
        author.setSurname(dto.surname());
        author.setBirthdate(dto.birthdate());
        return author;
    }
}


