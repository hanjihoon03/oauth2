//package com.example.oauth2.controller;
//
//import com.example.oauth2.domain.Member;
//import com.example.oauth2.domain.MemberRole;
//import com.example.oauth2.dto.LoginRequest;
//import com.example.oauth2.service.MemberService;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//
//
//import static org.assertj.core.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith(SpringExtension.class)
//// SessionController에 대한 Spring MVC 테스트를 구성
//@WebMvcTest(SessionController.class)
//@Slf4j
//class SessionControllerTest {
//
//    // MockMvc를 주입하여 HTTP 요청을 시뮬레이션
//    @Autowired
//    private MockMvc mockMvc;
//
//    // MemberService를 목(mock) 처리하여 실제 서비스를 호출하지 않고 동작을 시뮬레이션
//    @MockBean
//    private MemberService memberService;
//
//    // 테스트에서 사용할 Member와 LoginRequest 인스턴스 생성
//    private Member member;
//    private LoginRequest loginRequest;
//
//    // 각 테스트 메서드가 실행되기 전에 테스트 데이터를 초기화
//    @BeforeEach
//    public void setup() {
//        // Member 인스턴스 생성
//        member = new Member(1L, "1", "password", "name", MemberRole.USER);
//        // LoginRequest 인스턴스 생성
//        loginRequest = new LoginRequest();
//        loginRequest.setLoginId("1");
//        loginRequest.setPassword("password");
//    }
//
//
//    @Test
//    @DisplayName("로그인 시 세션 생성 여부를 검증하는 테스트")
//    void testLogin_SessionCreate() throws Exception {
//        // memberService의 login 메서드를 목 처리하여 member를 반환하도록 설정
//        Mockito.when(memberService.login(Mockito.any(LoginRequest.class))).thenReturn(member);
//
//        // loginRequest를 플래시 속성으로 포함하여 /session-login/login 경로로 POST 요청 수행
//        MvcResult result = mockMvc.perform(post("/session-login/login")
//                        .flashAttr("loginRequest", loginRequest))
//                // 리다이렉션 상태 반환 확인
//                .andExpect(status().is3xxRedirection())
//                // "/"로 리다이렉션 확인
//                .andExpect(redirectedUrl("/"))
//                .andReturn();
//
//        // 결과에서 세션을 가져옴
//        HttpSession session = result.getRequest().getSession();
//
//        log.info("LoginSessionId = {}", session.getAttribute("memberId"));
//        log.info("LoginMemberSessionId = {}", member.getId());
//        // 세션이 null이 아닌지 검증
//        assertThat(session).isNotNull();
//        // 세션에 올바른 memberId가 포함되어 있는지 검증
//        assertThat(session.getAttribute("memberId")).isEqualTo(member.getId());
//    }
//
//
//    @Test
//    @DisplayName("로그아웃 시 세션 무효화 여부를 검증하는 테스트")
//    void testLogout_SessionInvalidation() throws Exception {
//        // memberService의 login 메서드를 목 처리하여 member를 반환하도록 설정
//        Mockito.when(memberService.login(Mockito.any(LoginRequest.class))).thenReturn(member);
//
//        // loginRequest를 플래시 속성으로 포함하여 /session-login/login 경로로 POST 요청 수행
//        MvcResult loginResult = mockMvc.perform(post("/session-login/login")
//                        .flashAttr("loginRequest", loginRequest))
//                // 리다이렉션 상태 반환 확인
//                .andExpect(status().is3xxRedirection())
//                // "/"로 리다이렉션 확인
//                .andReturn();
//
//        // 결과에서 세션을 가져옴
//        HttpSession session = loginResult.getRequest().getSession();
//        // 세션이 null이 아닌지 검증
//        assertThat(session).isNotNull();
//
//        log.info("LogoutSessionId = {}", session.getAttribute("memberId"));
//        log.info("LogoutMemberSessionId = {}", member.getId());
//        // 세션에 올바른 memberId가 포함되어 있는지 검증
//        assertThat(session.getAttribute("memberId")).isEqualTo(member.getId());
//
//        // memberId를 세션 속성으로 포함하여 /session-login/logout 경로로 GET 요청 수행
//        MvcResult logoutResult = mockMvc.perform(get("/session-login/logout")
//                        .sessionAttr("memberId", member.getId()))
//                // 리다이렉션 상태 반환 확인
//                .andExpect(status().is3xxRedirection())
//                // "/"로 리다이렉션 확인
//                .andReturn();
//
//        // 로그아웃 후 세션을 가져옴
//        HttpSession sessionAfterLogout = logoutResult.getRequest().getSession(false);
//
//        log.info("sessionAfterLogout = {}", sessionAfterLogout);
//        // 세션이 null인지 검증하여 무효화되었음을 확인
//        assertThat(sessionAfterLogout).isNull();
//
//        // /session-login/info 경로로 GET 요청 수행하여 로그아웃 후 리다이렉션을 검증
//        MvcResult mvcResult = mockMvc.perform(get("/session-login/info")
//                        .sessionAttr("memberId", member.getId()))
//                // 리다이렉션 상태 반환 확인
//                .andExpect(status().is3xxRedirection())
//                // "/session-login/login"으로 리다이렉션 될 것을 확인
//                .andExpect(redirectedUrl("/session-login/login"))
//                .andReturn();
//
//        // 리다이렉션된 URL 로그 출력으로 검증
//        log.info("mvcResult={}", mvcResult.getResponse().getRedirectedUrl());
//    }
//}