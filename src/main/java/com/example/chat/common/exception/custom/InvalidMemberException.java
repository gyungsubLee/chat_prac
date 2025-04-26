package com.example.chat.common.exception.custom;


import com.example.chat.common.exception.BusinessException;
import com.example.chat.common.res.ErrorCode;

public class InvalidMemberException extends BusinessException {
    public InvalidMemberException() {
        super(ErrorCode.INVALID_MEMBER);
    }
}