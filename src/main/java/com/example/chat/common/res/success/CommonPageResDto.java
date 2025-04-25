package com.example.chat.common.res.success;




import com.example.chat.common.res.SuccessCode;
import org.springframework.data.domain.Page;

import java.util.List;

public record CommonPageResDto<T>(
        String code,
        String message,
        List<T> data,
        int page,           // 현재 페이지 번호
        int size,           // 페이지 당 항목 수
        long totalElements, // 전체 항목 수
        int totalPages      // 전체 페이지 수
) {
    public static <T> CommonPageResDto<T> of(SuccessCode code, Page<T> page) {
        return new CommonPageResDto<>(
                code.getCode(),
                code.getMessage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}