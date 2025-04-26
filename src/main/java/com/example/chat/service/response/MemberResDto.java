package com.example.chat.service.response;

import com.example.chat.common.res.success.BaseDtoType;
import com.example.chat.domain.Member;
import com.example.chat.domain.ROLE;

public record MemberResDto (
        Long memberId,
        String email,
        String name,
        ROLE role

) implements BaseDtoType  {
    public static MemberResDto from(Member member) {
        return new MemberResDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole()
        );
    }
}