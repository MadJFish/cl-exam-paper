package com.cupcake.learning.exampaper.exambook.resolver;

import com.cupcake.learning.exampaper.connection.CursorUtil;
import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import com.cupcake.learning.exampaper.exambook.repository.BookRepository;
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
public class BookQueryResolver implements GraphQLQueryResolver {
    private final CursorUtil cursorUtil;
    private final BookRepository bookRepository;

    public BookQueryResolver(CursorUtil cursorUtil, BookRepository bookRepository) {
        this.cursorUtil = cursorUtil;
        this.bookRepository = bookRepository;
    }

    public Book book(UUID id) {
        return bookRepository.findEagerlyById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find given book"));
    }

    public Connection<Book> books(int first, @Nullable String cursor) {
        Pageable pageable = PageRequest.of(0, first < 1 ? 20 : first);
        Page<Book> pageResult;
        if (cursor == null || cursor.isBlank()) {
            pageResult = bookRepository.findByOrderByIdAsc(pageable);
        } else {
            pageResult = bookRepository.findByIdAfterOrderByIdAsc(pageable, cursorUtil.decode(cursor));
        }

        List<Edge<Book>> edges = pageResult.getContent()
                .stream()
                .map(book -> new DefaultEdge<>(book, cursorUtil.createCursorWith(book.getId())))
                .collect(Collectors.toList());

        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                false,
                pageResult.hasNext());

        return new DefaultConnection<>(edges, pageInfo);
    }
}
