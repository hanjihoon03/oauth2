//package com.example.oauth2.controller;
//
//
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class SecurityLoginControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//    @Test
//    @DisplayName(value = "로그인 확인")
//    public void loginPageAccess() throws Exception {
//        mockMvc.perform(get("/security-login/login"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("login"));
//    }
//
//    @Test
//    @DisplayName(value = "유저 권한 확인")
//    @WithMockUser(username = "user", roles = {"USER"})
//    public void homePageAccessWithUserRole() throws Exception {
//        mockMvc.perform(get("/security-login"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("home"));
//    }
//
//    @Test
//    @DisplayName(value = "어드민 권한 확인")
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    public void adminPageAccessWithAdminRole() throws Exception {
//        mockMvc.perform(get("/security-login/admin"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin"));
//    }
//
//    @Test
//    @DisplayName(value = "인증 없이 관리자 페이지 접근 권한 확인")
//    public void accessDeniedToAdminPageWithoutAuth() throws Exception {
//        mockMvc.perform(get("/security-login/admin"))
//                .andExpect(status().is3xxRedirection());
//    }
//
//
//}