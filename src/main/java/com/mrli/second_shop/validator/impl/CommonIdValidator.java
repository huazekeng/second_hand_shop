package com.mrli.second_shop.validator.impl;



import com.mrli.second_shop.util.RegexpUtils;
import com.mrli.second_shop.validator.CommonId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器的一个轮子
 * 字符串使用正则表达式正整数的校验，加判空校验,空格校验
 *
 * @author narvik.su
 */
public class CommonIdValidator implements ConstraintValidator<CommonId, Object> {

    @Override
    public void initialize(CommonId arg0) {
    }

    @Override
    public boolean isValid(Object str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null) {
            rebuildConstraintValidation(constraintValidatorContext, "不能为空");
            return false;
        }
        if (str instanceof String && ((String) str).contains(" ")) {
            rebuildConstraintValidation(constraintValidatorContext, "不能包含空格");
            return false;
        }
        if (!RegexpUtils.isPositiveInteger(String.valueOf(str))) {
            rebuildConstraintValidation(constraintValidatorContext, "必须为数字");
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