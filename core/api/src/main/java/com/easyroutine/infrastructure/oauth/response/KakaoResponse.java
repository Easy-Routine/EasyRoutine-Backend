package com.easyroutine.infrastructure.oauth.response;

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
		Map<String, Object> properties = getProperties();
		Map<String, Object> profile = (LinkedHashMap) properties.get("profile");
		return profile.get("nickname").toString();
	}

	@Override
	public String getEmail() {
		Map<String, Object> properties = getProperties();
		return properties.get("email").toString();
	}

	@Override
	public String getProfileImage() {
		Map<String, Object> properties = getProperties();
		Map<String, Object> profile = getProfile(properties);
		return profile.get("profile_image_url").toString();
	}

	private Map<String, Object> getProfile(Map<String, Object> properties) {
		return (LinkedHashMap) properties.get("profile");
	}

	private Map<String, Object> getProperties() {
		return (LinkedHashMap) attribute.get("kakao_account");
	}

}
