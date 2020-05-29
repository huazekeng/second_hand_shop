package com.mrli.second_shop.validator;


 import com.mrli.second_shop.validator.impl.IsNumberValidator;

 import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsNumberValidator.class) // 具体的实现
@Target({java.lang.annotation.ElementType.METHOD,
        java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface IsNumber {

	String message() default "参数错误";

    // 下面这两个属性必须添加
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
