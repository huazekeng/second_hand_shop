package com.mrli.second_shop.security;


import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRolePermission;
import com.mrli.second_shop.service.ShsBackendRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @时间 2019-09-29 7:37
 *
 * 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private ShsBackendRolePermissionService backendRolePermissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getRequest().getMethod();
        //List<Menu> menus = menuService.getAllMenusWithRole();
        //获取该URL所需要的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object obj = authentication.getPrincipal();
        if (obj instanceof JwtUser) {
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            String username = jwtUser.getUsername();
            List<ShsBackendRole> rlist = backendRolePermissionService.getRolesByUrlAndUser(requestUrl, username, method);
            String[] roles = new String[rlist.size()];
            int index = 0;
            for (ShsBackendRole role : rlist) {
                roles[index] = role.getRoleStr();
                index++;
            }

            if (index > 0) {
                return SecurityConfig.createList(roles);
            }
            return SecurityConfig.createList("ROLE_LOGIN","ROLE_NO_PRIVILEGE");
        }
        //      for (Menu menu : menus) {
//            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
//                List<Role> roles = menu.getRoles();
//                String[] str = new String[roles.size()];
//                for (int i = 0; i < roles.size(); i++) {
//                    str[i] = roles.get(i).getName();
//                }
//                return SecurityConfig.createList(str);
//            }
//        }
        return SecurityConfig.createList("ROLE_ANONYMOUS");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
