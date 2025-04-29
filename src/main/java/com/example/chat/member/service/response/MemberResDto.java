package com.example.chat.member.service.response;

import com.example.chat.common.res.BaseDtoType;
import com.example.chat.member.domain.Member;

public record MemberResDto (
        Long memberId,
        String email,
        String name,
        String token

) implements BaseDtoType  {

    public static MemberResDto from(Member member, String token) {
        return new MemberResDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                token
        );
    }
}