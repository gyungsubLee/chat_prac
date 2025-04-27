package com.example.chat.common.exception;

import com.example.chat.common.exception.custom.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 인증(Auth)
    INVALID_AUTH_HEADER(Category.AUTH, HttpStatus.UNAUTHORIZED, "A001", "Authorization 헤더가 Bearer 타입이 아닙니다.", InvalidAuthHeaderException.class),
    INVALID_TOKEN(Category.AUTH, HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다.", InvalidTokenException.class),
    EXPIRED_TOKEN(Category.AUTH, HttpStatus.UNAUTHORIZED, "A003", "토큰이 만료되었습니다.", ExpiredJwtException.class),
    MALFORMED_TOKEN(Category.AUTH, HttpStatus.UNAUTHORIZED, "A004", "손상된 토큰입니다.", MalformedJwtException.class),

    // 회원(Member)
    EMAIL_ALREADY_EXISTS(Category.MEMBER, HttpStatus.CONFLICT, "M001", "이미 사용 중인 이메일입니다.", EmailAlreadyExistsException.class),
    EMAIL_NOT_FOUND(Category.MEMBER, HttpStatus.UNAUTHORIZED, "M002", "이메일 정보를 찾을 수 없습니다.", EmailNotFoundException.class),
    MEMBER_NOT_FOUND(Category.MEMBER, HttpStatus.NOT_FOUND, "M003", "회원 정보를 찾을 수 없습니다.", InvalidMemberException.class),
    INVALID_PASSWORD(Category.MEMBER, HttpStatus.UNAUTHORIZED, "M004", "유효하지 않은 비밀번호 입니다.", InvalidPasswordException.class),

    // 요청 ( Request )
    INVALID_INPUT_VALUE(Category.REQUEST, HttpStatus.BAD_REQUEST, "R001", "유효하지 않은 입력값 입니다.", MethodArgumentNotValidException.class),
    INVALID_HTTP_METHOD(Category.REQUEST, HttpStatus.BAD_REQUEST, "R002", "올바른 HTTP METHOD 를 입력해 주세요", HttpRequestMethodNotSupportedException.class),

    // 서버 오류
    INTERNAL_SERVER_ERROR(Category.SERVER, HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 내부 오류가 발생했습니다.", null);

    private final Category category;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final Class<? extends Throwable> exceptionClass;

    public enum Category {
        AUTH, MEMBER, REQUEST, SERVER
    }
}