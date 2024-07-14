package com.example.oauth2.repository;

import com.example.oauth2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);
    Member findByLoginId(String loginId);
}
