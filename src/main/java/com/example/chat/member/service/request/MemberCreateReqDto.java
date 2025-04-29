package com.example.chat.member.service.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateReqDto(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name
) {}
