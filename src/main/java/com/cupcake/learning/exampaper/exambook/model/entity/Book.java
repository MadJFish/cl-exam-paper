package com.cupcake.learning.exampaper.exambook.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NamedEntityGraph(name = "book-eager-fetch",
        attributeNodes = @NamedAttributeNode("exams")
)
@Entity
@Table(name = "book", schema = "public")
public class Book {
    @Id
    @GeneratedValue
    public UUID id;
    @ManyToMany
    private List<Exam> exams;
    private UUID authorId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
