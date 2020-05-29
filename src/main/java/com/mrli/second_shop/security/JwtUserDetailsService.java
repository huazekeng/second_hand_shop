package com.mrli.second_shop.security;

import com.mrli.second_shop.entity.ShsBackendManager;
import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.service.ShsBackendManagerService;
import com.mrli.second_shop.util.JsonUtils;
import com.mrli.second_shop.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录逻辑
 */
@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ShsBackendManagerService backendManagerService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        ShsBackendManager user =  backendManagerService.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户名不存在"));
        }

        //获取用户角色
        List<ShsBackendRole> roles = backendManagerService.getRole(username);
        roles.forEach((role) -> {
            GrantedAuthority au = new SimpleGrantedAuthority(role.getRoleStr());
            list.add(au);
        });
        /*if (user.getIsDeleted() == 1){
            throw new UserNotActiveException(ResultCodeEnum.USER_NOT_ACTIVE);
        }*/
        //123456 自定义MD5加密后=e10adc3949ba59abbe56e057f20f883e

        JwtUser JwtUser = new JwtUser(username,user.getMgPwd(),list);

        JwtUser.setIsEnable(user.getIsDeleted() == 0 ? true : false);

        log.info(JsonUtils.bean2Json(JwtUser));
        return JwtUser;
    }
}
