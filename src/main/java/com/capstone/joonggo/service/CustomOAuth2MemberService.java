package com.capstone.joonggo.service;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Role;
import com.capstone.joonggo.repository.MemberRepository;
import com.capstone.joonggo.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 서비스 구분을 위한 작업 (구글: google, 네이버: naver 등등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email;
        Map<String, Object> response = oAuth2User.getAttributes();

        if (registrationId.equals("google")) {
            email = (String) response.get("email");
        } else {
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        Member member;
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Long memberId;

        if (byEmail.isPresent()) {
            member = byEmail.get();
            memberId = member.getId();
        } else {
            Member createdMember = Member.createMember(null, null, null, null, null, email, Role.ROLE_USER);
            member = memberRepository.save(createdMember);
            memberId = member.getId();
        }

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, memberId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }
}
