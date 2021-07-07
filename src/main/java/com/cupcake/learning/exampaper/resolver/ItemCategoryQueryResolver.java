package com.cupcake.learning.exampaper.resolver;

import com.cupcake.learning.exampaper.sample.ItemCategory;
import com.cupcake.learning.exampaper.sample.ItemCategoryRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemCategoryQueryResolver implements GraphQLQueryResolver {
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryQueryResolver(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<String> findAllItemCategories() {
        return itemCategoryRepository.findAll()
                .stream()
                .map(ItemCategory::getCategory)
                .collect(Collectors.toList());
    }

    public String findItemCategoryByCategory(String category) {
        return itemCategoryRepository.findById(category)
                .map(ItemCategory::getCategory)
                .orElse("Nothing");
    }
}
