package com.capstone.joonggo.repository;

import com.capstone.joonggo.domain.Likes;
import com.capstone.joonggo.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    List<Likes> findByMemberId(Long memberID);
}
