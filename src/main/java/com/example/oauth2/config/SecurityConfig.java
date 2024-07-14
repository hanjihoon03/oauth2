//package com.example.oauth2.config;
//
//
//import com.example.oauth2.domain.MemberRole;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/security-login", "/security-login/login", "/security-login/join","/error/**").permitAll()
//                        .requestMatchers("/security-login/admin").hasRole(MemberRole.ADMIN.name())
//                        .requestMatchers("/security-login/info").hasAnyRole(MemberRole.ADMIN.name(),MemberRole.USER.name())
//                        .anyRequest()
//                        .authenticated());
//        http
//                .logout((auth) -> auth
//                        .logoutUrl("/security-login/logout")
//                );
//
//        http
//                .formLogin((auth) -> auth.loginPage("/security-login/login")
//                        .loginProcessingUrl("/security-login/loginProc")
//                        .failureUrl("/security-login/login")
//                        .defaultSuccessUrl("/security-login",true)
//                        .usernameParameter("loginId")
//                        .passwordParameter("password")
//                        .permitAll()
//                );
//
//
//        // csrf : 사이트 위변조 방지 설정 (스프링 시큐리티에는 자동으로 설정 되어 있음)
//        // csrf기능 켜져있으면 post 요청을 보낼때 csrf 토큰도 보내줘야 로그인 진행된다.
//        // 개발단계에서는 csrf 꺼두고 사용하고 서비스시 활성화 하는 것이 좋다.
//        http
//                .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
