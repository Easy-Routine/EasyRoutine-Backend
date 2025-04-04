package com.easyroutine.api.security.oauth;

import com.easyroutine.api.security.oauth.response.GoogleResponse;
import com.easyroutine.api.security.oauth.response.NaverResponse;
import com.easyroutine.api.security.oauth.response.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Response oAuth2Response = getOAuth2ResponseBy(registrationId, attributes);

        // TODO : add other roles
        return new CustomOAuth2User(oAuth2Response, "ROLE_USER");
    }

    private OAuth2Response getOAuth2ResponseBy(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google":
                return new GoogleResponse(attributes);
            case "naver":
                return new NaverResponse(attributes);
            default:
                throw new IllegalArgumentException("Unsupported OAuth2 provider: " + registrationId);
        }
    }
}
