package com.mrli.second_shop.validator.impl;


import com.mrli.second_shop.validator.CannotContainSpaces;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CannotContainSpacesValidator implements ConstraintValidator<CannotContainSpaces, String> {

    /**
     * 初始参数,获取注解中length的值
     */
    @Override
    public void initialize(CannotContainSpaces arg0) {
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(str)) {
            rebuildConstraintValidation(constraintValidatorContext, "不能为空");
            return false;
        }
        if (str.contains(" ")) {
            rebuildConstraintValidation(constraintValidatorContext, "不能包含空格");
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