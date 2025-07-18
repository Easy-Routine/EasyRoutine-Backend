package com.easyroutine.api.security;

import com.easyroutine.api.security.jwt.JsonWebTokenFilter;
import com.easyroutine.api.security.jwt.JsonWebTokenUtil;
import com.easyroutine.api.security.oauth.CustomOAuth2UserService;
import com.easyroutine.infrastructure.oauth.registration.OAuth2ClientRegistrationStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService oauth2UserService;

	private final OAuth2ClientRegistrationStorage clientRegistrationRepository;

	private final JsonWebTokenUtil jwtUtil;

	private final AuthenticationSuccessHandler authenticationSuccessHandler;

	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
			OAuth2ClientRegistrationStorage customClientRegistrationRepo,
			AuthenticationSuccessHandler authenticationSuccessHandler, JsonWebTokenUtil jwtUtil) {
		this.oauth2UserService = customOAuth2UserService;
		this.clientRegistrationRepository = customClientRegistrationRepo;
		this.jwtUtil = jwtUtil;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable);

		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
		http.cors(auth -> auth.configurationSource(request -> {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(
					Arrays.asList(
							"http://localhost:3000",
							"http://127.0.0.1:8080",
							"http://localhost:8080",
							"https://easyroutine.heykiwoung.com",
							"http://www.healper.shop",
							"https://www.healper.shop"
					)
			);
			configuration.setAllowCredentials(true);
			configuration.setAllowedHeaders(Arrays.asList("*"));
			configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			configuration.setMaxAge(3600L);
			return configuration;
		}));

		http.oauth2Login(oauth2 -> oauth2
			.clientRegistrationRepository(clientRegistrationRepository.clientRegistrationRepository())
			.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))
			.successHandler(authenticationSuccessHandler));

		http.addFilterBefore(new JsonWebTokenFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/oauth2/**", "/login/**")
			.permitAll()
			.requestMatchers("/h2-console/**")
			.permitAll()
			.requestMatchers("/swagger-ui/**")
			.permitAll()
			.requestMatchers("/**")
			.permitAll()
			.anyRequest()
			.authenticated());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
