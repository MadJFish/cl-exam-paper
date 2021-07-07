package com.cupcake.learning.exampaper.resolver;

import com.cupcake.learning.exampaper.sample.ItemCategory;
import com.cupcake.learning.exampaper.sample.ItemCategoryRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class ItemCategoryMutationResolver implements GraphQLMutationResolver {
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryMutationResolver(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategory addItemCategory(String category) {
        var itemCategory = new ItemCategory();
        itemCategory.setCategory(category);
        return itemCategoryRepository.save(itemCategory);
    }
}
