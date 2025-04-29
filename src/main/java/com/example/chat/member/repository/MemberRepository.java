package com.example.chat.member.repository;

import com.example.chat.common.exception.custom.EmailNotFoundException;
import com.example.chat.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    default Member findByEmailOrElseThrow(String email){
        return findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }
}

