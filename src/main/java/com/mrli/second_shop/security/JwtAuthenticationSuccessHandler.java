package com.mrli.second_shop.security;

import com.alibaba.fastjson.JSONObject;
import com.mrli.second_shop.config.RedisConfig;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.util.ResponseUtil;
import com.mrli.second_shop.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理模块
 */
@Component
@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        //从authentication中获取用户信息
        final JwtUser userDetail = (JwtUser) authentication.getPrincipal();
        //生成jwt
        String token =  JwtUtils.createJwtToken(userDetail.getUsername(),jwtConfig.getExpiresSecond());
        //生成refresh_token
        String refreshToken = JwtUtils.createJwtToken(UUID.randomUUID().toString(),jwtConfig.getRefreshExpiresSecond());
        httpServletResponse.addHeader("token", jwtConfig.getTokenHead()+ token);
        R result = R.ok().data("token",jwtConfig.getTokenHead() + " " + token).data("refresh_token",refreshToken);
        // 將token和userDetail緩存至Redis , 最長存放一天
        redisUtils.set(token, userDetail, 1l , TimeUnit.DAYS);
        redisUtils.set(refreshToken,userDetail.getUsername(),1l , TimeUnit.DAYS);
        ResponseUtil.ajaxResponse(httpServletResponse,result);
    }
}
