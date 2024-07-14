//package com.example.oauth2.controller;
//
//import com.example.oauth2.crypt.AESUtil;
//import com.example.oauth2.domain.Member;
//import com.example.oauth2.domain.MemberRole;
//import com.example.oauth2.dto.JoinRequest;
//import com.example.oauth2.dto.LoginRequest;
//import com.example.oauth2.service.MemberService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class MemberController {
//
//    private final MemberService memberService;
//
//    @GetMapping("")
//    public String home(@CookieValue(name = "memberId", required = false)
//                       String encryptedMemberId, Model model) {
//        log.info("memberId from cookie={}", encryptedMemberId);
//
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//
//        Long memberId = null;
//        if (encryptedMemberId != null) {
//            try {
//                memberId = Long.parseLong(AESUtil.decrypt(encryptedMemberId));
//            } catch (Exception e) {
//                log.error("Failed to decrypt cookie value", e);
//            }
//        }
//        Member loginMember = memberService.getLoginMemberById(memberId);
//        if (loginMember != null) {
//            model.addAttribute("name", loginMember.getName());
//        }
//
//        return "cookie/home";
//    }
//
//    @GetMapping("cookie-login/join")
//    public String joinPage(Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        model.addAttribute("joinRequest", new JoinRequest());
//        return "cookie/join";
//    }
//
//    @PostMapping("cookie-login/join")
//    public String join(@Valid @ModelAttribute JoinRequest joinRequest,
//                       BindingResult bindingResult, Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
//            bindingResult.addError(new FieldError(
//                    "joinRequest",
//                    "loginId",
//                    "ID가 존재합니다."
//            ));
//        }
//        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
//            bindingResult.addError(new FieldError(
//                    "joinRequest",
//                    "passwordCheck",
//                    "비밀번호가 일치하지 않습니다."
//            ));
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "cookie/join";
//        }
//
//        memberService.join(joinRequest);
//        return "redirect:/";
//    }
//
//    @GetMapping("cookie-login/login")
//    public String loginPage(Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        model.addAttribute("loginRequest", new LoginRequest());
//        return "cookie/login";
//    }
//
//    @PostMapping("cookie-login/login")
//    public String login(@ModelAttribute LoginRequest loginRequest,
//                        BindingResult bindingResult,
//                        HttpServletResponse response, Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        Member member = memberService.login(loginRequest);
//        Member admin = new Member("1","1","1",MemberRole.ADMIN);
//        memberService.save(admin);
//
//        if (member == null) {
//            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "cookie/login";
//        }
//
//        try {
//            // 쿠키 값 암호화
//            String encryptedMemberId = AESUtil.encrypt(String.valueOf(member.getId()));
//
//            Cookie cookie = new Cookie("memberId", encryptedMemberId);
//            cookie.setMaxAge(60 * 60);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//
//            log.info("member.name={}", member.getName());
//            log.info("Cookie set with memberId={}", encryptedMemberId);
//            log.info("Cookie ={}", cookie);
//        } catch (Exception e) {
//            log.error("Error encrypting member ID", e);
//        }
//
//
//        return "redirect:/";
//    }
//
//    @GetMapping("cookie-login/logout")
//    public String logout(HttpServletResponse response, Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        Cookie cookie = new Cookie("memberId", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return "redirect:/";
//    }
//
//    @GetMapping("cookie-login/info")
//    public String info(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        Member loginMember = memberService.getLoginMemberById(memberId);
//
//        if (loginMember == null) {
//            return "redirect:/cookie-login/login";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "cookie/info";
//    }
//
//    @GetMapping("cookie-login/admin")
//    public String adminPage(@CookieValue(name = "memberId", required = false) Long MemberId, Model model) {
//        model.addAttribute("loginType", "cookie-login");
//        model.addAttribute("pageName", "쿠키 로그인");
//
//        Member loginMember = memberService.getLoginMemberById(MemberId);
//
//        if (loginMember == null) {
//            return "redirect:/cookie-login/login";
//        }
//
//        if (!loginMember.getRole().equals(MemberRole.ADMIN)) {
//            return "redirect:/";
//        }
//        return "cookie/admin";
//    }
//}
