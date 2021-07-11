package com.cupcake.learning.exampaper.exambook.resolver;

import com.cupcake.learning.exampaper.connection.CursorUtil;
import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import com.cupcake.learning.exampaper.exambook.model.entity.Exam;
import com.cupcake.learning.exampaper.exambook.repository.ExamRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import io.micrometer.core.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExamQueryResolver implements GraphQLQueryResolver {
    private final CursorUtil cursorUtil;
    private final ExamRepository examRepository;

    public ExamQueryResolver(CursorUtil cursorUtil, ExamRepository examRepository) {
        this.cursorUtil = cursorUtil;
        this.examRepository = examRepository;
    }

    public Exam exam(UUID id) {
        return examRepository.findEagerlyById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find given book"));
    }

    public Connection<Exam> exams(int first, @Nullable String cursor) {
        Pageable pageable = PageRequest.of(0, first < 1 ? 20 : first);
        Page<Exam> pageResult;
        if (cursor == null || cursor.isBlank()) {
            pageResult = examRepository.findByOrderByIdAsc(pageable);
        } else {
            pageResult = examRepository.findByIdAfterOrderByIdAsc(pageable, cursorUtil.decode(cursor));
        }

        List<Edge<Exam>> edges = pageResult.getContent()
                .stream()
                .map(exam -> new DefaultEdge<>(exam, cursorUtil.createCursorWith(exam.getId())))
                .collect(Collectors.toList());

        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                false,
                pageResult.hasNext());

        return new DefaultConnection<>(edges, pageInfo);
    }
}
