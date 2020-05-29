package com.mrli.second_shop.handler;


import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.exception.BizException;
import com.mrli.second_shop.exception.SecondShopException;
import com.mrli.second_shop.util.ExceptionUtil;
import com.mrli.second_shop.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e){
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler(SecondShopException.class)
    @ResponseBody
    public R error(SecondShopException e){
        log.error(ExceptionUtil.getMessage(e));
        return R.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param fieldErrors 错误字段 List
     * @return ResultDTO
     */
    public static R handleArgumentException(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = fieldErrors.size(); i < size; i++) {
            FieldError fieldError = fieldErrors.get(i);
            sb.append(fieldError.getDefaultMessage());
            if (i != size - 1) {
                sb.append(";");
            }
        }
        return R.error().setResult(ResultCodeEnum.PARAM_ERROR);
    }

    private static R handleBindException(BindException e) {
        return handleArgumentException(e.getFieldErrors());
    }

    private static R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return handleArgumentException(e.getBindingResult().getFieldErrors());
    }

    public static R handleException(Exception e) throws Exception {
        // 處理參數異常
        if (e instanceof BindException) {
            return handleBindException((BindException) e);
        } else if (e instanceof BizException) {
            return handleBizException((BizException) e);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            return handleMethodArgumentNotValidException(methodArgumentNotValidException);
        } else {
            // 抛出其他異常，由上面處理
            throw e;
        }
    }

    private static R handleBizException(BizException e) {
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
