package com.mrli.second_shop.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUser implements UserDetails {
    private String username;
    private String password;

    // 賬戶是否激活
    private Boolean isEnable;

    // 賬戶是否過期
    private Boolean notExpire = true;

    // 密碼是否過期
    private Boolean credentialsNonExpired = true;

    // 賬戶是否被鎖
    private Boolean notLock = true;


    //权限
    private Collection<? extends GrantedAuthority> authorities;

    JwtUser(String username, String password, List<GrantedAuthority> authorities){
        this.username = username;
        this.password = password;
        this.authorities = authorities ;
    }

    public JwtUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 账户是否未过期
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.notExpire;
    }

    // 账户是否未锁定
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.notLock;
    }

    // 密码是否未过期
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    public boolean isEnabled() {
        return this.isEnable;
    }

    //获得当前登陆用户对应的对象。
    public static JwtUser getCurUser(){
        JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println("当前用户:"+userDetails);
        return userDetails;
    }
}

