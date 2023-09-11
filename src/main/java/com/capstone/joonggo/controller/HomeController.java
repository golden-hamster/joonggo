package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.dto.LoginDto;
import com.capstone.joonggo.dto.MemberJoinDto;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) LoginDto loginDto, Model model) {

        if (loginDto == null) {
            return "home";
        }

        Member member = memberService.findByLoginId(loginDto.getLoginId());

        if (member == null) {
            return "home";
        }

        //세션이 유지되면 market 으로 이동
        model.addAttribute("member", member);
        return "redirect:/market";

    }

    @GetMapping("/member/join")
    public String joinForm() {
        return "joinMember";
    }

    @PostMapping("/member/join")
    public String join(@Valid @ModelAttribute MemberJoinDto memberJoinDto, Model model) {
        Member member = Member.createMember(memberJoinDto.getLoginId(), memberJoinDto.getPassword(),
                memberJoinDto.getNickName(), memberJoinDto.getName(), memberJoinDto.getStudentId());
        memberService.join(member);
        model.addAttribute("memberName", member.getName());
        model.addAttribute("loginId", member.getLoginId());
        return "redirect:/";

    }

}
