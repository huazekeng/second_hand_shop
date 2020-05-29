package com.mrli.second_shop.exception;


import com.mrli.second_shop.constant.ResultCodeEnum;

public class BizException extends RuntimeException {
    private Integer code = ResultCodeEnum.UNKNOWN_REASON.getCode();

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BizException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
