package com.mrli.second_shop.validator.impl;


import com.mrli.second_shop.validator.ObjectArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 自定义校验器的一个轮子
 * 对象数组
 *
 * @author narvik.su
 */
public class ObjectArrayValidator implements ConstraintValidator<ObjectArray, Object> {

    @Override
    public void initialize(ObjectArray arg0) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj == null) {
            rebuildConstraintValidation(constraintValidatorContext, "不能为空");
            return false;
        }
        if (obj instanceof List && ((List) obj).size() == 0) {
            rebuildConstraintValidation(constraintValidatorContext, "的size必须大于0");
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