package com.project.library.repository;

import com.project.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {  // UUID kullanılıyor
}


