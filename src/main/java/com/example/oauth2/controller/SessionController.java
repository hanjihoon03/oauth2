//package com.example.oauth2.controller;
//
//
//import com.example.oauth2.domain.Member;
//import com.example.oauth2.domain.MemberRole;
//import com.example.oauth2.dto.JoinRequest;
//import com.example.oauth2.dto.LoginRequest;
//import com.example.oauth2.service.MemberService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
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
//public class SessionController {
//
//    private final MemberService memberService;
//
//    @GetMapping("")
//    public String home(Model model,
//                       @SessionAttribute(name = "memberId", required = false) Long memberId) {
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        Member loginMember = memberService.getLoginMemberById(memberId);
//
//        //로그인시 model에 이름 반환
//        if (loginMember != null) {
//            model.addAttribute("name", loginMember.getName());
//        }
//        return "session/home";
//    }
//
//    @GetMapping("session-login/join")
//    public String joinPage(Model model) {
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        model.addAttribute("joinRequest", new JoinRequest());
//        return "cookie/join";
//    }
//
//    @PostMapping("session-login/join")
//    public String join(@Valid @ModelAttribute JoinRequest joinRequest,
//                       BindingResult bindingResult, Model model) {
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
//            bindingResult.addError(new FieldError("joinRequest", "loginId", "ID가 존재합니다."));
//        }
//
//
//        // 비밀번호 = 비밀번호 체크 여부 확인
//        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
//            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "session/join";
//        }
//        memberService.join(joinRequest);
//
//        return "redirect:/";
//    }
//    @GetMapping("session-login/login")
//    public String loginPage(Model model) {
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        model.addAttribute("loginRequest", new LoginRequest());
//        return "cookie/login";
//    }
//
//    @PostMapping("session-login/login")
//    public String login(@ModelAttribute LoginRequest loginRequest,
//                        BindingResult bindingResult,
//                        HttpServletRequest request,
//                        HttpServletResponse response,
//                        Model model) {
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        Member member = memberService.login(loginRequest);
//        Member admin = new Member("1","1","1",MemberRole.ADMIN);
//        memberService.save(admin);
//
//        if (member == null) {
//            bindingResult.reject("loginFail","로그인 아이디 또는 비밀번호가 틀렸습니다.");
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "session/login";
//        }
//        // 기존의 세션을 무효화
//        request.getSession().invalidate();
//
//        //세션 생성
//        HttpSession session = request.getSession(true);
//        //세션에 memberId 속성 추가
//        session.setAttribute("memberId", member.getId());
//        //세션 유효기간 설정
//        session.setMaxInactiveInterval(60 * 60);
//
//        Cookie cookie = new Cookie("AutoLoginMemberId", member.getId().toString());
//        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일간 유지
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//
//        log.info("JSESSIONID={}", session.getAttribute("JSESSIONID"));
//        return "redirect:/";
//    }
//
//    @GetMapping("session-login/logout")
//    public String logout(HttpServletRequest request,HttpServletResponse response, Model model) {
//
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션로그인");
//
//        // request와 연관된 세션 불러옴 (없으면 null 반환)
//        HttpSession session = request.getSession(false);
//
//        // 세션이 존재
//        if (session != null) {
//            // 로그인 된 세션 무효화
//            session.invalidate();
//        }
//        Cookie cookie = new Cookie("Cookie-memberId", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        return "redirect:/";
//    }
//
//    @GetMapping("session-login/info")
//    public String memberInfo(@SessionAttribute(name = "memberId", required = false) Long memberId, Model model) {
//
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션로그인");
//
//        Member loginMember = memberService.getLoginMemberById(memberId);
//
//        if (loginMember == null) {
//            return "redirect:/session-login/login";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "session/info";
//    }
//
//    @GetMapping("session-login/admin")
//    public String adminPage(@SessionAttribute(name = "memberId", required = false) Long memberId, Model model) {
//
//        model.addAttribute("loginType", "session-login");
//        model.addAttribute("pageName", "세션 로그인");
//
//        Member loginMember = memberService.getLoginMemberById(memberId);
//
//        if(loginMember == null) {
//            return "redirect:/session-login/login";
//        }
//
//        if(!loginMember.getRole().equals(MemberRole.ADMIN)) {
//            return "redirect:/session-login";
//        }
//
//        return "session/admin";
//    }
//
//}
