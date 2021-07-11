package com.cupcake.learning.exampaper.exambook.resolver;

import com.cupcake.learning.exampaper.PatchModelMapper;
import com.cupcake.learning.exampaper.auth.repository.UserRepository;
import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import com.cupcake.learning.exampaper.exambook.model.entity.Exam;
import com.cupcake.learning.exampaper.exambook.model.input.ExamInput;
import com.cupcake.learning.exampaper.exambook.repository.BookRepository;
import com.cupcake.learning.exampaper.exambook.repository.ExamRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExamMutationResolver implements GraphQLMutationResolver {
    private final PatchModelMapper mapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ExamRepository examRepository;

    public ExamMutationResolver(PatchModelMapper mapper, UserRepository userRepository, BookRepository bookRepository, ExamRepository examRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.examRepository = examRepository;
    }

    public Exam addExam(UUID authorId, ExamInput input) {
        userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Unable to find given user"));

        var exam = new Exam();
        mapper.map(input, exam);
        exam.setAuthorId(authorId);
        exam.setBooks(getUpdatedBundledIn(input.getBundledIn()));
        return examRepository.save(exam);
    }

    public Exam updateExam(UUID examId, ExamInput input) {
        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Unable to find given exam"));
        mapper.map(input, exam);
        exam.setBooks(getUpdatedBundledIn(input.getBundledIn()));
        return examRepository.save(exam);
    }

    public Exam publishExam(UUID examId) {
        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Unable to find given exam"));
        exam.setActive(true);
        return examRepository.save(exam);
    }

    public Exam cancelExam(UUID examId) {
        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Unable to find given exam"));
        exam.setActive(false);
        return examRepository.save(exam);
    }

    private List<Book> getUpdatedBundledIn(Set<UUID> bundledIn) {
        if (bundledIn == null) {
            return null;
        }

        return bundledIn.stream()
                .map(bookRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
