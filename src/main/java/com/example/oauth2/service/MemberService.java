package com.example.oauth2.service;

import com.example.oauth2.domain.Member;
import com.example.oauth2.dto.JoinRequest;
import com.example.oauth2.dto.LoginRequest;
import com.example.oauth2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);

    }
    public void save(Member member) {
        memberRepository.save(member);
    }

    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if (findMember == null) {
            return null;
        }
//        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
//            return null;
//        }
        return findMember;
    }

    public Member getLoginMemberById(Long memberId) {
        if (memberId == null) return null;

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);
    }
    public Member getLoginMemberByLoginId(String loginId) {
        if (loginId == null) return null;

        return memberRepository.findByLoginId(loginId);
    }

    public void securityJoin(JoinRequest joinRequest) {
        if (memberRepository.existsByLoginId(joinRequest.getLoginId())) {
            return;
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        memberRepository.save(joinRequest.toEntity());
    }
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}

