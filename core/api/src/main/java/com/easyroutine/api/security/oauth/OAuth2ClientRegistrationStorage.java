package com.easyroutine.api.security.oauth;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class OAuth2ClientRegistrationStorage {

    private final OAuthClientRegistration oAuthClientRegistration;

    public OAuth2ClientRegistrationStorage(OAuthClientRegistration oAuthClientRegistration) {
        this.oAuthClientRegistration = oAuthClientRegistration;
    }

    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                oAuthClientRegistration.googleClientRegistration()
        );
    }
}
