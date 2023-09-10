package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Comment;
import com.capstone.joonggo.service.CommentService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;


}
