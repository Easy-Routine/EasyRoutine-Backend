package com.easyroutine.api.security.oauth.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getName() {
        Map properties = getProperties();
        Map profile = (LinkedHashMap) properties.get("profile");
        return profile.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        Map properties = getProperties();
        return properties.get("email").toString();
    }

    @Override
    public String getProfileImage() {
        Map properties = getProperties();
        return properties.get("profile_image").toString();
    }

    private Map getProperties() {
        return (LinkedHashMap) attribute.get("kakao_account");
    }
}
