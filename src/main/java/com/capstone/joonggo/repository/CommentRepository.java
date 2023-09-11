package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Comment;
import com.capstone.joonggo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
}
