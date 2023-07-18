package com.mysite.sbb.common.config;

import co.elastic.clients.elasticsearch.security.User;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 최종 로그인 시간 업데이트
        Member member = memberRepository.findByMemberId(userDetails.getUsername());
        member.setLastLoginTime(LocalDateTime.now());
        memberRepository.save(member);

        // 사용자의 권한에 따라 리다이렉트
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_ADMIN")) { // 관리자 로그인 시 관리자 페이지로
                response.sendRedirect("/admin/");
                return;
            } else if (auth.getAuthority().equals("ROLE_MEMBER")) { // 멤버 로그인 시 메인화면으로
                response.sendRedirect("/");
                return;
            }
        }

        // 권한이 없는 경우 기본 리다이렉트
        response.sendRedirect("/");
    }
}
