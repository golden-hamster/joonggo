package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Comment;
import com.capstone.joonggo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public void update(Long id, String content) {
        Comment comment = commentRepository.findById(id).get();
        comment.setContent(content);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }


}
