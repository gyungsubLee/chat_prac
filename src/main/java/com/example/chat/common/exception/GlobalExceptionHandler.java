package com.example.chat.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse<String>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ErrorCode errorCode = ExceptionResolver.resolveErrorCode(ex);
        return buildErrorResponse(request, errorCode, errorCode.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorCode errorCode = ExceptionResolver.resolveErrorCode(ex);
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));
        return buildErrorResponse(request, errorCode, fieldErrors);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorCode errorCode = ExceptionResolver.resolveErrorCode(ex);
        return buildErrorResponse(request, errorCode, errorCode.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse<String>> handleUnknownException(Throwable ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Unexpected server error", ex);
        return buildErrorResponse(request, errorCode, errorCode.getMessage());
    }

    private <T> ResponseEntity<ErrorResponse<T>> buildErrorResponse(HttpServletRequest request, ErrorCode errorCode, T message) {
        String traceId = UUID.randomUUID().toString();
        String path = request.getRequestURI();
        String method = request.getMethod();

        ErrorResponse<T> errorResponse = ErrorResponse.from(errorCode, message, path, method, traceId);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}