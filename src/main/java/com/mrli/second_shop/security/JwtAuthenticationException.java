package com.mrli.second_shop.security;


import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.util.ResponseUtil;
import com.mrli.second_shop.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring security其他异常处理类,比如请求路径不存在等，
 * 如果不配置此类，则Spring security默认会跳转到登录页面
 */
@Component
@Slf4j
public class JwtAuthenticationException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("AuthenticationEntryPoint检测到异常:"+e);
        ResponseUtil.ajaxResponse(httpServletResponse,R.setResult(ResultCodeEnum.USER_NOT_LOGIN));
    }
}

