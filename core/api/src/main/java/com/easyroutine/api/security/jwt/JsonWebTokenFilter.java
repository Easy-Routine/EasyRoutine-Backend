package com.easyroutine.api.security.jwt;

import com.easyroutine.api.security.oauth.CustomOAuth2User;
import com.easyroutine.api.security.oauth.CustomOAuth2UserImpl;
import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenUtil jwtUtil;

    public JsonWebTokenFilter(JsonWebTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (hasNotToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7).trim();

        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String memberId = jwtUtil.getMemberId(token);
        String role = jwtUtil.getRole(token);

        Member member = Member.of(memberId, MemberRole.MEMBER);
        CustomOAuth2User customOAuth2User = new CustomOAuth2UserImpl(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean hasNotToken(String token) {
        return token == null || !token.startsWith("Bearer ");
    }
}
