package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberNickName(String nickName);
}
