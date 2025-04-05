package com.easyroutine.api.security.jwt;

import com.easyroutine.api.security.oauth.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final JsonWebTokenUtil jwtUtil;

    public JsonWebTokenFilter(JsonWebTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        List<Cookie> cookiesList = cookies != null ? Arrays.asList(cookies) : List.of();

        Cookie authorization = cookiesList.stream()
                .filter(cookie -> "Authorization".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        if (hasNotAuthorizationCookie(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.getValue();

        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(username, role);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private static boolean hasNotAuthorizationCookie(Cookie authorization) {
        return authorization == null;
    }
}
