package com.easyroutine.api.security.oauth.response;

import java.util.Map;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getName();

    String getEmail();
}
