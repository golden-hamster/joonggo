package com.capstone.joonggo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
public class Member implements UserDetails {

    @GeneratedValue
    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String nickName;

    private Integer studentId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private LocalDateTime createdDate;

    public static Member createMember(String email, String password,String nickName, String name,
                                      Integer studentId, Role role, LoginType loginType) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.name = name;
        member.nickName = nickName;
        member.studentId = studentId;
        member.role = role;
        member.loginType = loginType;
        member.createdDate = LocalDateTime.now();
        return member;
    }


    /**
     * Spring Security
     */

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override // 사용자의 id 반환(고유한 값)
    public String getUsername() {
        return email;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        return true; // true : 만료 X
    }

    @Override // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        return true; // true : 잠금 X
    }

    @Override // 패스워드 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        return true; // true : 만료 X
    }

    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        return true; // true : 사용 가능
    }

}
