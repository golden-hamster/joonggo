package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.dto.LoginDto;
import com.capstone.joonggo.service.LoginService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        HttpServletRequest request) {
        log.info("=================LoginController===============");
        if (bindingResult.hasErrors()) {
            return "login";
        }

//        Member member = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        Member member = memberService.findByEmail(loginDto.getEmail());
        log.info("member.getId() = {}", member.getId());
        boolean isPasswordMatch = passwordEncoder.matches(loginDto.getPassword(), member.getPassword());
        if (!isPasswordMatch) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login";
        }

        //로그인 성공 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        return "redirect:/market";
    }

//    @PostMapping("/login")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        log.info("================loginSuccess============");
        String email = userDetails.getUsername();
        Member member = memberService.findByEmail(email);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());
        return "redirect:/market";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션 삭제
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


}
