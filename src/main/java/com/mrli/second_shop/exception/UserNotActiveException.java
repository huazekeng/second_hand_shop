package com.mrli.second_shop.exception;

import com.mrli.second_shop.constant.ResultCodeEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;

/**
 * 用戶未登錄異常
 */

public class UserNotActiveException extends AuthenticationException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserNotActiveException(ResultCodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }


}
