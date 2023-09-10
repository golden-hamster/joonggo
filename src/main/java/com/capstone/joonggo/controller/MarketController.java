package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MarketController {

    private final PostService postService;

    @GetMapping("/market")
    public String findAllPosts() {
        List<Post> posts = postService.findAll();
        return "market";
    }


}
