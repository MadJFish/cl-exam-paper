package com.cupcake.learning.exampaper.auth.resolver;

import com.cupcake.learning.exampaper.auth.model.User;
import com.cupcake.learning.exampaper.auth.repository.UserRepository;
import com.cupcake.learning.exampaper.connection.CursorUtil;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import io.micrometer.core.lang.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserQueryResolver implements GraphQLQueryResolver {
    private final CursorUtil cursorUtil;
    private final UserRepository userRepository;

    public UserQueryResolver(CursorUtil cursorUtil, UserRepository userRepository) {
        this.cursorUtil = cursorUtil;
        this.userRepository = userRepository;
    }

    public Connection<User> users(int first, @Nullable String cursor) {
        Pageable pageable = PageRequest.of(0, first < 1 ? 20 : first);
        Page<User> pageResult;
        if (cursor == null || cursor.isBlank()) {
            pageResult = userRepository.findByOrderByIdAsc(pageable);
        } else {
            pageResult = userRepository.findByIdAfterOrderByIdAsc(pageable, cursorUtil.decode(cursor));
        }

        List<Edge<User>> edges = pageResult.getContent()
                .stream()
                .map(user -> new DefaultEdge<>(user, cursorUtil.createCursorWith(user.getId())))
                .collect(Collectors.toList());

        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                false,
                pageResult.hasNext());

        return new DefaultConnection<>(edges, pageInfo);
    }
}
