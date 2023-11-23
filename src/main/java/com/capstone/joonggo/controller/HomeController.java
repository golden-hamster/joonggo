package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.*;
import com.capstone.joonggo.dto.MemberDto;
import com.capstone.joonggo.dto.MemberPostDto;
import com.capstone.joonggo.dto.MemberJoinDto;
import com.capstone.joonggo.service.LikesService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final LikesService likesService;

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
        return "joinMember2";
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
    public String memberPage(@PathVariable String nickName, Model model,
                             @PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC)Pageable pageable) {
        Member member = memberService.findByNickName(nickName);
        Page<Post> posts = postService.findByNickName(nickName, pageable);

        List<MemberPostDto> memberPostDtoList = new ArrayList<>();
        for (Post post : posts) {
            MemberPostDto memberDto = convertToMemberDto(post);
            memberPostDtoList.add(memberDto);
        }
        MemberDto memberDto = new MemberDto(nickName);

        model.addAttribute("posts", memberPostDtoList);
        model.addAttribute("member", memberDto);

        return "member";
    }

    @GetMapping("/member/{nickName}/likes")
    public String memberLikesPage(@PathVariable String nickName, Model model) {
        Member member = memberService.findByNickName(nickName);
        List<Post> posts = likesService.findByMemberId(member.getId());

        List<MemberPostDto> memberPostDtoList = new ArrayList<>();
        for (Post post : posts) {
            MemberPostDto memberDto = convertToMemberDto(post);
            memberPostDtoList.add(memberDto);
        }
        MemberDto memberDto = new MemberDto(nickName);

        model.addAttribute("posts", memberPostDtoList);
        model.addAttribute("member", memberDto);

        return "likes";
    }

    public MemberPostDto convertToMemberDto(Post post) {
        List<UploadFile> uploadFiles = post.getUploadFiles();
        String thumbnailName = null;
        if (uploadFiles != null && !uploadFiles.isEmpty()) {
            UploadFile uploadFile = uploadFiles.get(0);
            thumbnailName = uploadFile.getStoreName();
        } else {
//            thumbnailName = "default.png";
            thumbnailName = "default.jpg";
        }

        return new MemberPostDto(post.getTitle(), post.getPrice(), post.getId(), thumbnailName, post.getStatus(), post.getCreatedDate());

    }
}
