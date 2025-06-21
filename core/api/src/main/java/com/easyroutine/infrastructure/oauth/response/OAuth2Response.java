package com.easyroutine.infrastructure.oauth.response;

import java.util.Map;

public interface OAuth2Response {

	String getProvider();

	String getProviderId();

	String getName();

	String getEmail();

	String getProfileImage();

}
