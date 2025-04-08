package com.easyroutine.api.security;

import com.easyroutine.api.security.jwt.JsonWebTokenUtil;
import com.easyroutine.api.security.oauth.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JsonWebTokenUtil jwtUtil;

    public AuthenticationSuccessHandler(JsonWebTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = getRoleIn(authorities);

        String token = jwtUtil.createJwt(username, role, 60*60*60L);
        Cookie authorization = createCookie("Authorization", token);
        response.setHeader("Authorization", token);

        response.addCookie(authorization);
        response.sendRedirect("http://localhost:3000/");
    }

    private String getRoleIn(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No authorities found"));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
//        cookie.setSecure(true); // TODO : need to set the secure flag
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
