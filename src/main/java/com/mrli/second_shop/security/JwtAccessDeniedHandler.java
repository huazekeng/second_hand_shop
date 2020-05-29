package com.mrli.second_shop.security;

import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.util.ResponseUtil;
import com.mrli.second_shop.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理模块
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        log.info("权限不足：" + e.getMessage());
        ResponseUtil.ajaxResponse(response,R.setResult(ResultCodeEnum.PERMISSION_DENIED));
    }
}

