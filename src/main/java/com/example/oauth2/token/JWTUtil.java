package com.example.oauth2.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey secretKey;

    /**
     * JWTUtil 생성자.
     * @param secret 환경 설정 파일에서 가져온 비밀 키 문자열.
     * 비밀 키를 SecretKeySpec을 통해 SecretKey 객체로 변환합니다.
     */
    public JWTUtil(@Value("${jwt.secret_key}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * JWT 토큰에서 loginId를 추출합니다.
     * @param token JWT 토큰.
     * @return 추출된 loginId.
     */
    public String getLoginId(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 검증
                .getBody();

        return claim.get("loginId", String.class); // loginId 추출
    }

    /**
     * JWT 토큰에서 role을 추출합니다.
     * @param token JWT 토큰.
     * @return 추출된 role.
     */
    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 검증
                .getBody();

        return claims.get("role", String.class); // role 추출
    }

    /**
     * JWT 토큰이 만료되었는지 확인합니다.
     * @param token JWT 토큰.
     * @return 토큰이 만료되었으면 true, 그렇지 않으면 false.
     */
    public Boolean isExpired(String token) {
        log.info("secretKey={}", secretKey);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 검증
                .getBody()
                .getExpiration()
                .before(new Date()); // 만료 시간과 현재 시간 비교
    }

    /**
     * JWT 토큰을 생성합니다.
     * @param loginId 사용자 로그인 아이디.
     * @param role 사용자 역할.
     * @param expiredMs 토큰 만료 시간 (밀리초).
     * @return 생성된 JWT 토큰.
     */
    public String createJwt(String loginId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("loginId", loginId) // loginId 클레임 추가
                .claim("role", role) // role 클레임 추가
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘 및 키 설정
                .compact(); // 토큰 생성
    }
}
