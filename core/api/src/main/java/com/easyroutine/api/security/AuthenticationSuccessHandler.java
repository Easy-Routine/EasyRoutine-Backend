package com.easyroutine.api.security;

import com.easyroutine.api.security.jwt.JsonWebTokenUtil;
import com.easyroutine.infrastructure.oauth.CustomOAuth2UserImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JsonWebTokenUtil jwtUtil;

	public AuthenticationSuccessHandler(JsonWebTokenUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		CustomOAuth2UserImpl customUserDetails = (CustomOAuth2UserImpl) authentication.getPrincipal();
		String memberId = customUserDetails.getMemberId();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = getRoleIn(authorities);

		String token = jwtUtil.createAccessToken(memberId, role, 60 * 60 * 60L);
		String refreshToken = jwtUtil.createRefreshToken(memberId, 60 * 60 * 24 * 30L);

		String targetUrl = UriComponentsBuilder.fromUriString("http://www.healper.shop")
			.queryParam("token", token)
			.queryParam("refreshToken", refreshToken)
			.build()
			.toUriString();

		response.sendRedirect(targetUrl);
	}

	private String getRoleIn(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No authorities found"));
	}

}
