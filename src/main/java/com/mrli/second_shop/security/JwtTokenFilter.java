package com.mrli.second_shop.security;

import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.exception.SecondShopException;
import com.mrli.second_shop.util.JsonUtils;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.util.ResponseUtil;
import com.mrli.second_shop.vo.R;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器，请求到达Controller前进行Token判断
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("进入token过滤器");
        String authHeader = httpServletRequest.getHeader(jwtConfig.getHeader());
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String[] whiteList = jwtConfig.getWhiteList();
        if (HttpMethod.OPTIONS.name().equals(httpServletRequest.getMethod()) || antPathMatcher.match("/**/logout",httpServletRequest.getServletPath())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (whiteList.length > 0) {
            for (String path : whiteList) {
                if (antPathMatcher.match(path, httpServletRequest.getServletPath())) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }


        try {
            if (authHeader != null && authHeader.startsWith(jwtConfig.getTokenHead())) {
                String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
                Claims Claims = JwtUtils.parseJWT(authToken);
                String username = Claims.getId();
                log.info("username:" + username);
                //验证token,具体怎么验证看需求，可以只验证token不查库，把权限放在jwt中即可
                // UserDetails UserDetails = jwtUserDetailsService.loadUserByUsername(username);
                if(JwtUtils.isTokenExpired(Claims)){//token过期
                    log.info("token过期" + authToken);
                    // 從redis 中移除 token
                    // redisUtils.remove(authToken);
                    throw new SecondShopException(ResultCodeEnum.TOKEN_EXPIRE);
                }else{
                    if(httpServletRequest.getRequestURI().contains("api")) {
                        log.info("api token没过期，放行" + authToken);
                        filterChain.doFilter(httpServletRequest, httpServletResponse);
                    } else {
                        log.info("token没过期，放行" + authToken);
                        JwtUser UserDetails = (JwtUser) redisUtils.get(authToken);
                        //这里只要告诉springsecurity权限即可，账户密码就不用提供验证了，这里我们把UserDetails传给springsecurity，以便以后我们获取当前登录用户
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(UserDetails, null, UserDetails.getAuthorities());
                        //                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, UserDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        log.info(String.format("Authenticated userDetail %s, setting security context", username));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
//            else {
//                throw new SecondShopException(ResultCodeEnum.LOGIN_EXPIRES);
//            }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (SecondShopException  ex){
            R resultDTO = R.error();
            resultDTO.setMessage(ex.getMessage());
            resultDTO.setCode(ex.getCode());
            ResponseUtil.ajaxResponse(httpServletResponse,resultDTO);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            R resultDTO = R.error().setResult(ResultCodeEnum.LOGIN_EXPIRES);
            ResponseUtil.ajaxResponse(httpServletResponse,resultDTO);
        }
    }

}
