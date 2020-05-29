package com.mrli.second_shop.aop;

import com.mrli.second_shop.handler.GlobalExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
@Order(3)
public class BindingResultAspect {
    @Pointcut("execution(public * com.mrli.second_shop.controller.*.*Controller.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    return GlobalExceptionHandler.handleArgumentException(result.getFieldErrors());
                }
            }
        }
        return joinPoint.proceed();
    }
}
