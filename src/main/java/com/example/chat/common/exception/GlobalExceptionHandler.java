package com.example.chat.common.exception;

import com.example.chat.common.res.ErrorCode;
import com.example.chat.common.res.fail.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse<String>> handleBusinessException(BusinessException ex) {
        ErrorCode code = ex.getErrorCode();

        log.warn("BusinessException 발생 - code: {}, message: {}", code.getCode(), ex.getMessage(), ex);

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ErrorResponse.of(code, code.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> messageMap = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // 중복 시 첫 번째 유지
                ));

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, messageMap));
    }
}
