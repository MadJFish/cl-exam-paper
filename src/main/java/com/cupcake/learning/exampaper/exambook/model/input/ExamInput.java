package com.cupcake.learning.exampaper.exambook.model.input;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class ExamInput {
    private Set<UUID> bundledIn;
    private String name;
    private String description;
    private BigDecimal price;
    private String subject;
    private Integer durationInMinutes;

    public Set<UUID> getBundledIn() {
        return bundledIn;
    }

    public void setBundledIn(Set<UUID> bundledIn) {
        this.bundledIn = bundledIn;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
