package com.mrli.second_shop.exception;

import com.mrli.second_shop.constant.ResultCodeEnum;
import lombok.Data;

@Data
public class SecondShopException extends RuntimeException {

    private int code;

    public SecondShopException(int code, String message){
        super(message);
        this.code = code;
    }

    public SecondShopException(ResultCodeEnum rce){
        super(rce.getMessage());
        this.code = rce.getCode();
    }

    @Override
    public String toString() {
        return "SecondShopException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
