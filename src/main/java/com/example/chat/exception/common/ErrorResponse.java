package com.example.chat.exception.common;


import java.time.LocalDateTime;

public record ErrorResponse<T>(
        String code,
        T message,
        int httpStatus,
        LocalDateTime timestamp
) {

    // 단일 에러 메시지만 있는 경우
    public static <T> ErrorResponse<T> of(ErrorCode errorCode, T message) {
        return new ErrorResponse<>(
                errorCode.getCode(),
                message,
                errorCode.getHttpStatus().value(),
                LocalDateTime.now()
        );
    }
}
