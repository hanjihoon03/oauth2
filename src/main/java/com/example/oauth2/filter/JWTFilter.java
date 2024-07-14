package com.example.oauth2.filter;

import com.example.oauth2.domain.Member;
import com.example.oauth2.domain.MemberRole;
import com.example.oauth2.userdetails.CustomUserDetails;
import com.example.oauth2.token.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 authorization 헤더를 찾는다.
        String authorization = request.getHeader("Authorization");

        //Authorization 헤더 검증
        //비어 있거나 Bearer로 시작하지 않는 경우
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("token = null");
            //토큰이 유효하지 않기 때문에 request와 response를 다음 필터로 넘긴다.
            filterChain.doFilter(request, response);

            return;
        }

        //Authorization에서 Bearer 접두사 제거
        String token = authorization.split(" ")[1];

        log.info("token={}", token);
        //token 소멸 시간 검증
        //유효기간이 만료한 경우
        if (jwtUtil.isExpired(token)) {
            log.info("token expired");
            filterChain.doFilter(request, response);

            return;
        }
        //token 검증 완료로 일시적인 세션을 생성해 세션에 user 정보 설정
        String loginId = jwtUtil.getLoginId(token);
        String role = jwtUtil.getRole(token);

        Member member = new Member();
        member.setLoginId(loginId);
        // 매번 요청마다 DB 조회해서 password 초기화 할 필요 없기에 정확한 비밀번호 넣을 필요 없음
        // 따라서 임시 비밀번호 설정
        member.setPassword("임시 비밀번호");
        member.setRole(MemberRole.valueOf(role));

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록해 일시적으로 user 세션 생성
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
