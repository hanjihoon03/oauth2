//package com.example.oauth2.config;
//
//import com.example.oauth2.filter.JWTFilter;
//import com.example.oauth2.filter.LoginFilter;
//import com.example.oauth2.token.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class TokenSecurityConfig {
//    private final AuthenticationConfiguration configuration;
//    private final JWTUtil jwtUtil;
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/jwt-login", "/jwt-login/", "/jwt-login/login", "/jwt-login/join").permitAll()
//                        .requestMatchers("/jwt-login/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated());
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(configuration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        // 로그인 필터 이전에 JWTFilter를 넣음
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
//
//
//        return http.build();
//    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//
//
//        return new BCryptPasswordEncoder();
//    }
//}
