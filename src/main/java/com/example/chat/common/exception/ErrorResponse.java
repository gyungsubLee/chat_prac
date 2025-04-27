package com.example.chat.common.exception;


import java.time.LocalDateTime;

public record ErrorResponse<T>(
        String code,
        T message,
        int httpStatus,
        String category,
        String path,
        String method,
        String traceId,
        LocalDateTime timestamp
) {

    public static <T> ErrorResponse<T> from(ErrorCode errorCode, T message, String path, String method, String traceId) {
        if (errorCode == null) {
            throw new IllegalArgumentException("ErrorCode must not be null");
        }
        return new ErrorResponse<>(
                errorCode.getCode(),
                message,
                errorCode.getHttpStatus().value(),
                errorCode.getCategory().name(),
                path,
                method,
                traceId,
                LocalDateTime.now()
        );
    }
}
