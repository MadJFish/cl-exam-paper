package com.cupcake.learning.exampaper.sample;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemCategory {
    @Id
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
