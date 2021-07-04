package com.cupcake.learning.exampaper.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemCategoryController {
    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping
    public List<String> findAll() {
        return itemCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable String id) {
        return itemCategoryService.findById(id);
    }
}
