package com.cupcake.learning.exampaper.exambook.repository;

import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findByOrderByIdAsc(Pageable pageable);
    Page<Book> findByIdAfterOrderByIdAsc(Pageable pageable, UUID id);

    @EntityGraph(value = "book-eager-fetch")
    Optional<Book> findEagerlyById(UUID id);
}
