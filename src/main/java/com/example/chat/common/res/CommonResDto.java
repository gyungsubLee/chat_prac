package com.example.chat.common.res;

public record CommonResDto<T>(
        String code,
        String message,
        T data
) {
    public static <T> CommonResDto<T> of(SuccessCode successCode, T data) {
        return new CommonResDto<>(
                successCode.getCode(),
                successCode.getMessage(),
                data
        );
    }

    public static <T> CommonResDto<T> of(SuccessCode successCode) {
        return new CommonResDto<>(
                successCode.getCode(),
                successCode.getMessage(),
                null
        );
    }
}