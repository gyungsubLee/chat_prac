package com.example.chat.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "E001", "이미 사용 중인 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "회원 정보를 찾을 수 없습니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001","");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
