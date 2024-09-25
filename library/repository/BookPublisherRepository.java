package com.project.library.repository;

import com.project.library.model.Book;
import com.project.library.model.BookPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookPublisherRepository extends JpaRepository<BookPublisher, UUID> {
    List<BookPublisher> findByBook(Book book);
}

