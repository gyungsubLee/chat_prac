package com.example.chat.exception;

import com.example.chat.exception.common.BusinessException;
import com.example.chat.exception.common.ErrorCode;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
