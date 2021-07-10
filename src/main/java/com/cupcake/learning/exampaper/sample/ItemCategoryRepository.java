package com.cupcake.learning.exampaper.sample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, String> {
    Page<ItemCategory> findByOrderByCategoryAsc(Pageable pageable);
    Page<ItemCategory> findByCategoryAfterOrderByCategoryAsc(Pageable pageable, String category);
}
