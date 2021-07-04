package com.cupcake.learning.exampaper.resolver;

import com.cupcake.learning.exampaper.sample.ItemCategoryService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityQueryResolver implements GraphQLQueryResolver {
    private final ItemCategoryService itemCategoryService;

    public EntityQueryResolver(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    public List<String> findAllItemCategories(Pageable pageable) {
        return itemCategoryService.findAll(pageable);
    }

    public String findItemCategoryByCategory(String category) {
        return itemCategoryService.findById(category);
    }
}
