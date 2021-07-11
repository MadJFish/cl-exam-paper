package com.cupcake.learning.exampaper.exambook.model.input;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class BookInput {
    private Set<UUID> consistsOf;
    private String name;
    private String description;
    private BigDecimal price;

    public Set<UUID> getConsistsOf() {
        return consistsOf;
    }

    public void setConsistsOf(Set<UUID> consistsOf) {
        this.consistsOf = consistsOf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
