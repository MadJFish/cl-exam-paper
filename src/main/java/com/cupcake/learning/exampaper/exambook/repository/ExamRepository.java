package com.cupcake.learning.exampaper.exambook.repository;

import com.cupcake.learning.exampaper.exambook.model.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
    Page<Exam> findByOrderByIdAsc(Pageable pageable);
    Page<Exam> findByIdAfterOrderByIdAsc(Pageable pageable, UUID id);

    @EntityGraph(value = "exam-eager-fetch")
    Optional<Exam> findEagerlyById(UUID id);
}
