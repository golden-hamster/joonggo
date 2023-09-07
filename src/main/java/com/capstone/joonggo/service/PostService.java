package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).get();
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }
}
