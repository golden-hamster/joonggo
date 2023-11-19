package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Likes;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.repository.LikesRepository;
import com.capstone.joonggo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public List<Post> findByMemberId(Long memberId) {
        List<Likes> likesList = likesRepository.findByMemberId(memberId);
        List<Post> posts = new ArrayList<>();
        for (Likes likes : likesList) {
            Long postId = likes.getPostId();
            Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다"));
            posts.add(post);
        }

        return posts;
    }

    @Transactional
    public Long save(Long memberId, Long postId) {
        Likes likes = Likes.createLikes(memberId, postId);
        Likes savedLikes = likesRepository.save(likes);
        return savedLikes.getId();
    }

    @Transactional
    public void delete(Long memberId, Long postId) {
        List<Likes> likesList = likesRepository.findByMemberId(memberId);
        likesList.stream()
                .filter(likes -> likes.getPostId().equals(postId))
                .forEach(likes -> likesRepository.delete(likes));

    }
}
