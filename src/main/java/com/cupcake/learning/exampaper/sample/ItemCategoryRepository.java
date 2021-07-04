package com.cupcake.learning.exampaper.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, String> {

    @Override
    List<ItemCategory> findAll();

    @Override
    Optional<ItemCategory> findById(String s);
}
