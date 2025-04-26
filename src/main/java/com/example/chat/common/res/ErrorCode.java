package com.example.chat.common.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "E001", "이미 사용 중인 이메일입니다."),
    EEMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "이메일 정보를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "E003", "회원 정보를 찾을 수 없습니다."),
    INVALID_MEMBER(HttpStatus.UNAUTHORIZED, "E004", "이메일 또는 비밀번호가 일치하지 않습니다."),


    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001","");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}