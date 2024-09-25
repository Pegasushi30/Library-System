package com.project.library.repository;

import com.project.library.model.Book;
import com.project.library.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, UUID> { // UUID kullanılıyor
    List<BookAuthor> findByBook(Book book);
}



