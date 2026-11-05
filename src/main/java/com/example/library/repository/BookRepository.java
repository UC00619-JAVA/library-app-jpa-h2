package com.example.library.repository;

import java.util.List;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Book, Long>: entity Book, primary key of type Long
// save, findAll, findById, count, deleteById... already exist
public interface BookRepository extends JpaRepository<Book, Long> {

    // Derived methods: Spring Data reads the method name and writes the SQL
    List<Book> findByAuthor(String author);

    List<Book> findByPublishedYearGreaterThan(int year);

    List<Book> findByTitleContainingIgnoreCase(String text);
}
