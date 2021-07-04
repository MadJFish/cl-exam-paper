package com.cupcake.learning.exampaper.resolver;

import com.cupcake.learning.exampaper.sample.ItemCategory;
import com.cupcake.learning.exampaper.sample.ItemCategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class EntityMutationResolver implements GraphQLMutationResolver {
    private final ItemCategoryService itemCategoryService;

    public EntityMutationResolver(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    public ItemCategory addItemCategory(String category) {
        return itemCategoryService.add(category);
    }
}
