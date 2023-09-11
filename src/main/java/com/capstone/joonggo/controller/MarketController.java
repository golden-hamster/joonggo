package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.dto.LoginDto;
import com.capstone.joonggo.dto.MarketDto;
import com.capstone.joonggo.service.CommentService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MarketController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/market")
    public String market(Model model) {
        List<Post> posts = postService.findAll();
        List<MarketDto> marketDtoList = new ArrayList<>();
        for (Post post : posts) {
            MarketDto marketDto = convertToMarketDto(post);
            marketDtoList.add(marketDto);
        }
        model.addAttribute("posts", marketDtoList);
        return "market";
    }

    @GetMapping("/market/{postId}")
    public String postForm(@PathVariable(name = "postId") Long PostId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto,
                           Model model) {
        return "post";
    }

    @GetMapping("/market/create")
    public String createPostForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto,
                                 Model model) {
        Member member = memberService.findByLoginId(loginDto.getLoginId());
        String name = member.getName();
        model.addAttribute("name", name);
        return "createPost";
    }

    public MarketDto convertToMarketDto(Post post) {
        return new MarketDto(post.getTitle(), post.getPrice(), post.getId(), post.getCreatedTime());
    }

}
