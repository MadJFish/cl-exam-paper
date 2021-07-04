package com.cupcake.learning.exampaper.sample;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<String> findAll(Pageable pageable) {
        return itemCategoryRepository.findAll(pageable).stream().map(ItemCategory::getCategory).collect(Collectors.toList());
    }

    public String findById(String id) {
        return itemCategoryRepository.findById(id).map(ItemCategory::getCategory).orElse("Nothing");
    }

    public ItemCategory add(String category) {
        var itemCategory = new ItemCategory();
        itemCategory.setCategory(category);
        return itemCategoryRepository.save(itemCategory);
    }
}
