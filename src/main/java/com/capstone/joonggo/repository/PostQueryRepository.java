package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.search.PostSearch;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.capstone.joonggo.domain.QPost.*;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public PostQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    /*
    public List<Post> findAll(PostSearch search) {
        return query.select(post)
                .from(post)
                .where(
                        likeTitle(search.getTitle())
                )
                .fetch();
    }

     */

    public Page<Post> findAll(PostSearch search, Pageable pageable) {
        List<Post> posts = query.select(post)
                .from(post)
                .where(
                        likeTitle(search.getTitle())
                )
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.selectFrom(post)
                .where(
                        likeTitle(search.getTitle())
                )
                .fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }

    private BooleanExpression likeTitle(String title) {
        if (StringUtils.hasText(title)) {
            return (post.title.like("%" + title + "%"));
        }
        return null;
    }


}
