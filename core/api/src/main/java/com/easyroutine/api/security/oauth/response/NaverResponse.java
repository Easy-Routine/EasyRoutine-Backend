package com.easyroutine.api.security.oauth.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        Map response = getResponse();
        return response.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map response = getResponse();
        return response.get("email").toString();
    }

    @Override
    public String getProfileImage() {
        Map response = getResponse();
        return response.get("profile_image").toString();
    }

    @Override
    public String getName() {
        Map response = getResponse();
        return response.get("name").toString();
    }

    private Map getResponse() {
        Map response = (LinkedHashMap) attribute.get("response");
        return response;
    }
}
