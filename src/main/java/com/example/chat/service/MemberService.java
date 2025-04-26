package com.example.chat.service;

import com.example.chat.domain.Member;
import com.example.chat.common.exception.custom.EmailAlreadyExistsException;
import com.example.chat.repository.MemberRepository;
import com.example.chat.service.request.MemberCreateReqDto;
import com.example.chat.service.response.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResDto create(MemberCreateReqDto reqDto) {
        if (memberRepository.findByEmail(reqDto.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String hashedPassword = passwordEncoder.encode(reqDto.password());

        Member member = Member.builder()
                .email(reqDto.email())
                .password(hashedPassword)
                .name(reqDto.name())
                .build();

        return MemberResDto.from(memberRepository.save(member));
    }
}
