package com.capstone.joonggo.config;

import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.service.MemberDetailService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final MemberDetailService memberDetailService;
//    @Bean // 스프링 시큐리티 기능 비활성화
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/static/**");
//    }

    @Bean // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspect) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspect);
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvc.pattern("/member/join"), mvc.pattern("/"),
                                mvc.pattern("/market"), mvc.pattern("/images/**"))
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/market")
                        .successHandler(
                                new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        Authentication authentication) throws IOException, ServletException {
                                        if (authentication.getPrincipal() instanceof Member) {
                                            Member member = (Member) authentication.getPrincipal();
                                            Long memberId = member.getId();
                                            HttpSession session = request.getSession();
                                            session.setAttribute(SessionConst.LOGIN_MEMBER, memberId);
                                        }

                                        response.sendRedirect("/market");
                                    }
                                }
                        )
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/market")
                        .invalidateHttpSession(true))
                .csrf(csrf -> csrf.disable());

        return http.getOrBuild();
    }

    @Bean // 인증 관리자 관련 설정
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(memberDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean // 패스워드 인코더로 사용할 빈 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
