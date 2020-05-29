package com.mrli.second_shop.security;

import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.util.ResponseUtil;
import com.mrli.second_shop.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        String token = req.getHeader(jwtConfig.getHeader());
        token = token.substring(jwtConfig.getTokenHead().length() + 1);
        String refresh_token = req.getParameter("refresh_token");
        // 清除Redis 的 token和refresh_token
        redisUtils.remove(token);
        redisUtils.remove(refresh_token);
        ResponseUtil.ajaxResponse(resp, R.setResult(ResultCodeEnum.LOGOUT_SUCCESS));
    }
}
