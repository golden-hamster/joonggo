package com.capstone.joonggo;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.service.CommentService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        private final CommentService commentService;

        public void dbInit() {
            Member member1 = Member.createMember("test1", "test", "test1", "test", 123);
            Member member2 = Member.createMember("test2", "test", "test2", "test", 124);
            Member member3 = Member.createMember("test3", "test", "test3", "test", 125);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
        }
    }
}
