package com.example.chat.common.exception.custom;

import com.example.chat.common.exception.BusinessException;
import com.example.chat.common.res.ErrorCode;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException() {
        super(ErrorCode.EEMAIL_NOT_FOUND);
    }
}
