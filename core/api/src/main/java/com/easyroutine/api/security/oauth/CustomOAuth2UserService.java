package com.easyroutine.api.security.oauth;

import com.easyroutine.domain.member.Member;
import com.easyroutine.domain.member.MemberRole;
import com.easyroutine.infrastructure.oauth.CustomOAuth2UserImpl;
import com.easyroutine.infrastructure.oauth.response.GoogleResponse;
import com.easyroutine.infrastructure.oauth.response.KakaoResponse;
import com.easyroutine.infrastructure.oauth.response.NaverResponse;
import com.easyroutine.infrastructure.oauth.response.OAuth2Response;
import com.easyroutine.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuth2Response oAuth2Response = getOAuth2ResponseBy(registrationId, attributes);

		// TODO : add other roles
		Member member = Member.of(oAuth2Response, MemberRole.MEMBER);

		String provider = oAuth2Response.getProvider();
		String providerId = oAuth2Response.getProviderId();
		Optional<Member> existMember = memberRepository.findByProviderAndProviderId(provider, providerId);

		Member authenticatedMember = existMember.get();

		if (existMember.isEmpty()) {
			authenticatedMember = memberRepository.save(member);
		}

		return new CustomOAuth2UserImpl(authenticatedMember);
	}

	private OAuth2Response getOAuth2ResponseBy(String registrationId, Map<String, Object> attributes) {
		switch (registrationId) {
			case "google":
				return new GoogleResponse(attributes);
			case "naver":
				return new NaverResponse(attributes);
			case "kakao":
				return new KakaoResponse(attributes);
			default:
				throw new IllegalArgumentException("Unsupported OAuth2 provider: " + registrationId);
		}
	}

}
