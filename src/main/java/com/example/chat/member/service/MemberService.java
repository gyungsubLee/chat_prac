package com.example.chat.member.service;

import com.example.chat.common.auth.JwtTokenProvider;
import com.example.chat.common.exception.custom.InvalidMemberException;
import com.example.chat.member.domain.Member;
import com.example.chat.common.exception.custom.EmailAlreadyExistsException;
import com.example.chat.member.repository.MemberRepository;
import com.example.chat.member.service.request.MemberCreateReqDto;
import com.example.chat.member.service.request.MemberLoginReqDto;
import com.example.chat.member.service.response.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void create(MemberCreateReqDto reqDto) {
        if (memberRepository.findByEmail(reqDto.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String hashedPassword = passwordEncoder.encode(reqDto.password());

        Member member = Member.builder()
                .email(reqDto.email())
                .password(hashedPassword)
                .name(reqDto.name())
                .build();

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberResDto login(MemberLoginReqDto reqDto) {

        Member findMember = memberRepository.findByEmailOrElseThrow(reqDto.email());

        if(!isValidMember(reqDto.password(), findMember.getPassword())) {
            throw new InvalidMemberException();
        }

        // 일치하는 경우, access token 발행
        String token = jwtTokenProvider.createToken(findMember.getEmail(), String.valueOf(findMember.getRole()));

        return MemberResDto.from(findMember, token);
    }

    private boolean isValidMember(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
