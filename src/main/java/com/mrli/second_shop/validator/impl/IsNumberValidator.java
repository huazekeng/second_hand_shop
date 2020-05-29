package com.mrli.second_shop.validator.impl;


import com.mrli.second_shop.util.RegexpUtils;
import com.mrli.second_shop.validator.IsNumber;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

    @Override
    public void initialize(IsNumber arg0) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            rebuildConstraintValidation(context, "不能为空");
            return false;
        }
        if (!RegexpUtils.isPositiveInteger(value)) {
            rebuildConstraintValidation(context, "必须为数字");
            return false;
        }
        return true;
    }

    private void rebuildConstraintValidation(ConstraintValidatorContext constraintValidatorContext, String str) {
        constraintValidatorContext.disableDefaultConstraintViolation();// 禁用默认的message的值
        // 重新添加错误提示语句
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(str).addConstraintViolation();
    }

}
