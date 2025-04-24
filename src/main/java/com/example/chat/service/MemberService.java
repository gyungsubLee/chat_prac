package com.example.chat.service;

import com.example.chat.domain.Member;
import com.example.chat.common.exception.custom.EmailAlreadyExistsException;
import com.example.chat.repository.MemberRepository;
import com.example.chat.service.request.MemberCreateReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long create(MemberCreateReqDto reqDto) {
        if (memberRepository.findByEmail(reqDto.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        // TODO: password 암호화
        String hashedPassword = "암호화된 비밀번호";

        Member member = Member.builder()
                .email(reqDto.email())
                .password(hashedPassword)
                .name(reqDto.name())
                .build();

        return memberRepository.save(member).getId();
    }
}
