package com.example.chat.exception.common;


import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        int httpStatus,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getHttpStatus().value(),
                LocalDateTime.now()
        );
    }
}
