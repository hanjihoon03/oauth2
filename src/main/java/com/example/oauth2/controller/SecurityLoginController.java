//package com.example.oauth2.controller;
//
//import com.example.oauth2.domain.Member;
//import com.example.oauth2.dto.JoinRequest;
//import com.example.oauth2.dto.LoginRequest;
//import com.example.oauth2.service.MemberService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Collection;
//import java.util.Iterator;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/security-login")
//public class SecurityLoginController {
//
//    private final MemberService memberService;
//
//    @GetMapping(value = {"", "/"})
//    public String home(Model model) {
//
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//
//        Member loginMember = memberService.getLoginMemberByLoginId(loginId);
//
//
//        if (loginMember != null) {
//            model.addAttribute("name", loginMember.getName());
//        }
//
//        return "home";
//    }
//
//    @GetMapping("/join")
//    public String joinPage(Model model) {
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        model.addAttribute("joinRequest", new JoinRequest());
//        return "join";
//
//    }
//    @PostMapping("/join")
//    public String join(@Valid @ModelAttribute JoinRequest joinRequest,
//                       BindingResult bindingResult, Model model) {
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        memberService.securityJoin(joinRequest);
//
//        return "redirect:/security-login";
//
//    }
//
//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        model.addAttribute("loginRequest", new LoginRequest());
//
//        return "login";
//    }
//
//    @GetMapping("/info")
//    public String memberInfo(Authentication auth, Model model) {
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        Member loginMember = memberService.getLoginMemberByLoginId(auth.getName());
//
//        model.addAttribute("member", loginMember);
//        return "info";
//    }
//    @GetMapping("/admin")
//    public String adminPage(Model model) {
//        model.addAttribute("loginType", "security-login");
//        model.addAttribute("pageName", "스프링 시큐리티 로그인");
//
//        return "admin";
//    }
//
//}
