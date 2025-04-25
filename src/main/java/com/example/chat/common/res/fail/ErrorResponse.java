package com.example.chat.common.res.fail;


import com.example.chat.common.res.ErrorCode;

import java.time.LocalDateTime;

public record ErrorResponse<T>(
        int httpStatus,
        String code,
        T message,
        LocalDateTime timestamp
) {

    // 단일 에러 메시지만 있는 경우
    public static <T> ErrorResponse<T> of(ErrorCode errorCode, T message) {
        return new ErrorResponse<>(
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                message,
                LocalDateTime.now()
        );
    }
}
