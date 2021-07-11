package com.cupcake.learning.exampaper.exambook.resolver;

import com.cupcake.learning.exampaper.PatchModelMapper;
import com.cupcake.learning.exampaper.auth.repository.UserRepository;
import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import com.cupcake.learning.exampaper.exambook.model.entity.Exam;
import com.cupcake.learning.exampaper.exambook.model.input.BookInput;
import com.cupcake.learning.exampaper.exambook.repository.BookRepository;
import com.cupcake.learning.exampaper.exambook.repository.ExamRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookMutationResolver implements GraphQLMutationResolver {
    private final PatchModelMapper mapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ExamRepository examRepository;

    public BookMutationResolver(PatchModelMapper mapper, UserRepository userRepository, BookRepository bookRepository, ExamRepository examRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.examRepository = examRepository;
    }

    public Book addBook(UUID authorId, BookInput input) {
        userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Unable to find given user"));

        var book = new Book();
        mapper.map(input, book);
        book.setAuthorId(authorId);
        book.setExams(getUpdatedConsistOf(input.getConsistsOf()));
        return bookRepository.save(book);
    }

    public Book updateBook(UUID bookId, BookInput input) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Unable to find given book"));
        mapper.map(input, book);
        book.setExams(getUpdatedConsistOf(input.getConsistsOf()));
        return bookRepository.save(book);
    }

    public Book publishBook(UUID bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Unable to find given book"));
        book.setActive(true);
        return bookRepository.save(book);
    }

    public Book cancelBook(UUID bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Unable to find given book"));
        book.setActive(false);
        return bookRepository.save(book);
    }

    private List<Exam> getUpdatedConsistOf(Set<UUID> consistsOf) {
        if (consistsOf == null) {
            return null;
        }

        return consistsOf.stream()
                .map(examRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
