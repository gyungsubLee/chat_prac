package com.example.chat.exception.common;

import com.example.chat.exception.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorCode code = ex.getErrorCode();

        log.warn("BusinessException 발생 - code: {}, message: {}", code.getCode(), ex.getMessage(), ex);

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ErrorResponse.of(code));
    }
}
