package com.cupcake.learning.exampaper.resolver;

import com.cupcake.learning.exampaper.connection.CursorUtil;
import com.cupcake.learning.exampaper.sample.ItemCategory;
import com.cupcake.learning.exampaper.sample.ItemCategoryRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import io.micrometer.core.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemCategoryQueryResolver implements GraphQLQueryResolver {
    private final CursorUtil cursorUtil;
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryQueryResolver(CursorUtil cursorUtil, ItemCategoryRepository itemCategoryRepository) {
        this.cursorUtil = cursorUtil;
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

    public Connection<ItemCategory> itemCategories(int first, @Nullable String cursor) {
        Pageable pageable = PageRequest.of(0, first < 1 ? 20 : first);
        String validCursor;
        if (cursor == null || cursor.isBlank()) {
            validCursor = "";
        } else {
            validCursor = cursor;
        }

        Page<ItemCategory> pageResult = itemCategoryRepository.findByCategoryAfterOrderByCategoryAsc(pageable, cursorUtil.testDecode(validCursor));
        List<Edge<ItemCategory>> edges = pageResult.getContent()
                .stream()
                .map(itemCategory -> new DefaultEdge<>(itemCategory, cursorUtil.testEncode(itemCategory.getCategory())))
                .collect(Collectors.toList());

        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                false,
                pageResult.hasNext());

        return new DefaultConnection<>(edges, pageInfo);
    }
}
