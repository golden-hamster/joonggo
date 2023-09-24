package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.UploadFile;
import com.capstone.joonggo.repository.PostQueryRepository;
import com.capstone.joonggo.repository.PostRepository;
import com.capstone.joonggo.search.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;


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
/*
    public List<Post> findAll(PostSearch search, Pageable pageable) {
        List<Post> posts = postQueryRepository.findAll(search);
        return posts;
    }
*/

    public Page<Post> findAll(PostSearch search, Pageable pageable) {
        return postQueryRepository.findAll(search, pageable);
    }
    
    
    @Transactional
    public void update(Long postId, String title, String content, Integer price, List<UploadFile> uploadFiles) {
        Post post = postRepository.findById(postId).orElse(null);
        post.update(title, content, price, uploadFiles);
    }
}
