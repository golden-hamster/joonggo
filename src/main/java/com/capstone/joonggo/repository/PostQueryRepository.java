package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.search.PostSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.capstone.joonggo.domain.QPost.*;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public PostQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    public List<Post> findAll(PostSearch search) {
        return query.select(post)
                .from(post)
                .where(
                        likeTitle(search.getTitle())
                )
                .fetch();
    }

    private BooleanExpression likeTitle(String title) {
        if (StringUtils.hasText(title)) {
            return (post.title.like("%" + title + "%"));
        }
        return null;
    }

}
