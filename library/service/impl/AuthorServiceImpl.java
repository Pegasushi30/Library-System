package com.project.library.service.impl;


import com.project.library.dto.AuthorDto;
import com.project.library.exception.AuthorNotFoundException;
import com.project.library.mapper.AuthorMapper;
import com.project.library.model.Author;
import com.project.library.repository.AuthorRepository;
import com.project.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDto> getAuthorById(UUID id) {
        return authorRepository.findById(id).map(authorMapper::toDto);
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = authorMapper.toModel(authorDto);
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDto updateAuthor(UUID id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found for ID: " + id));
        author.setName(authorDto.name());
        author.setSurname(authorDto.surname());
        author.setBirthdate(authorDto.birthdate());
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    public void deleteAuthor(UUID id) {
        authorRepository.deleteById(id);
    }
}

