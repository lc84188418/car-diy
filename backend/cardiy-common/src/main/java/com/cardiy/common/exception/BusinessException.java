package com.cardiy.common.exception;

import com.cardiy.common.constant.RespCodeEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(RespCodeEnum respCodeEnum) {
        super(respCodeEnum.getMessage());
        this.code = Integer.valueOf(respCodeEnum.getCode());
    }

    public BusinessException(RespCodeEnum respCodeEnum, String arg) {
        super(respCodeEnum.getMessage() + ":[" + arg + "]");
        this.code = Integer.valueOf(respCodeEnum.getCode());
    }

}


