package com.easyroutine.api.security;

import com.easyroutine.api.security.oauth.CustomOAuth2UserService;
import com.easyroutine.api.security.oauth.OAuth2ClientRegistrationStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;
    private final OAuth2ClientRegistrationStorage clientRegistrationRepository;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, OAuth2ClientRegistrationStorage customClientRegistrationRepo) {

        this.oauth2UserService = customOAuth2UserService;
        this.clientRegistrationRepository = customClientRegistrationRepo;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((form) -> form.disable())
                .httpBasic((httpBasic) -> httpBasic.disable());

        http
                .oauth2Login((oauth2) -> oauth2
                        .clientRegistrationRepository(clientRegistrationRepository.clientRegistrationRepository())
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(oauth2UserService)));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
