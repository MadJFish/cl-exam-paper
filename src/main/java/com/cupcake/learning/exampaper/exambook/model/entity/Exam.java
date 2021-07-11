package com.cupcake.learning.exampaper.exambook.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NamedEntityGraph(name = "exam-eager-fetch",
        attributeNodes = @NamedAttributeNode("books")
)
@Entity
@Table(name = "exam", schema = "public")
public class Exam {
    @Id
    @GeneratedValue
    public UUID id;
    @ManyToMany(mappedBy = "exams")
    private List<Book> books;
    private UUID authorId;
    private String name;
    private String description;
    private BigDecimal price;
    private String subject;
    private Integer durationInMinutes;
    private Boolean isActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
