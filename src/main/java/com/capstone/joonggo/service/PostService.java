package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.PostStatus;
import com.capstone.joonggo.domain.UploadFile;
import com.capstone.joonggo.dto.CreatePostDto;
import com.capstone.joonggo.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final PostQueryRepository postQueryRepository;



    @Transactional
    public Long save(CreatePostDto createPostDto, List<UploadFile> uploadFiles) {
        Member member = memberRepository.findById(createPostDto.getMemberId()).get();
        Post post = Post.createPost(member, createPostDto.getTitle(), createPostDto.getTitle(), createPostDto.getPrice(),
                PostStatus.SALE, uploadFiles);

        return postRepository.save(post).getId();
    }



    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public Page<Post> findAll(PostSearch search, Pageable pageable) {
        return postQueryRepository.findAll(search, pageable);
    }

    public Page<Post> findByNickName(String nickName, Pageable pageable) {
        return postQueryRepository.findByNickName(nickName, pageable);
    }
    
    @Transactional
    public void update(Long postId, String title, String content, Integer price, List<UploadFile> uploadFiles) {
        Post post = postRepository.findById(postId).orElse(null);
        post.update(title, content, price, uploadFiles);
    }

    @Transactional
    public void updateStatus(Long postId,PostStatus status) {
        Post post = postRepository.findById(postId).orElse(null);
        post.updateStatus(status);
    }

    @Transactional
    public void addLikesCount(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."))
                .addLikesCount();
    }

    @Transactional
    public void subLikesCount(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."))
                .subLikesCount();
    }
}
