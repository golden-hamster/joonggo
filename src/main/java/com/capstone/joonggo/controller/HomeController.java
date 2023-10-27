package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.*;
import com.capstone.joonggo.dto.MarketDto;
import com.capstone.joonggo.dto.MemberDto;
import com.capstone.joonggo.dto.MemberJoinDto;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        Member member = memberService.findById(memberId);

        if (member == null) {
            return "home";
        }

        //세션이 유지되면 market 으로 이동
        return "redirect:/market";

    }

    @GetMapping("/member/join")
    public String joinForm() {
        return "joinMember";
    }

    @PostMapping("/member/join")
    public String join(@Valid @ModelAttribute MemberJoinDto memberJoinDto, Model model
    , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinMember";
        }
        memberService.join(memberJoinDto);
        return "redirect:/";
    }

    @GetMapping("/member/{nickName}")
    public String memberPage(@PathVariable String nickName) {
        Member member = memberService.findByNickName(nickName);
        List<Post> posts = postService.findByNickName(nickName);

        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Post post : posts) {
            MemberDto memberDto = convertToMemberDto(post);
            memberDtoList.add(memberDto);
        }

        return "member";
    }

    public MemberDto convertToMemberDto(Post post) {
        List<UploadFile> uploadFiles = post.getUploadFiles();
        String thumbnailName = null;
        if (uploadFiles != null && !uploadFiles.isEmpty()) {
            UploadFile uploadFile = uploadFiles.get(0);
            thumbnailName = uploadFile.getStoreName();
        } else {
//            thumbnailName = "default.png";
            thumbnailName = "default.jpg";
        }

        return null;

    }
}
