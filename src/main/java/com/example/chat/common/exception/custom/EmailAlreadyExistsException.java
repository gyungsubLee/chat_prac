package com.example.chat.common.exception.custom;

import com.example.chat.common.exception.BusinessException;
import com.example.chat.common.exception.ErrorCode;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
