package com.easyroutine.repository.member;

import com.easyroutine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	Optional<Member> findByNickname(String nickname);

}
