package com.easyroutine.api.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JsonWebTokenUtil {

    private SecretKey secretKey;

    public JsonWebTokenUtil(@Value("${jwt.secret}") String rawSecretKey) {
        byte[] key = rawSecretKey.getBytes(StandardCharsets.UTF_8);
        String algorithm = Jwts.SIG.HS256.key().build().getAlgorithm();
        this.secretKey = new SecretKeySpec(key, algorithm);
    }

    public String getMemberId(String token) {
        Claims payload = getClaimsBy(token);
        return payload.get("memberId", String.class);
    }

    public String getRole(String token) {
        Claims payload = getClaimsBy(token);
        return payload.get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            Claims payload = getClaimsBy(token);
            return payload.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("토큰이 만료되었습니다.");
            return true;
        } catch (Exception e) {
            log.error("토큰 검증에 실패했습니다.", e);
            return true;
        }
    }

    public String createJwt(String memberId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    private Claims getClaimsBy(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);
        return claimsJws.getPayload();
    }
}
