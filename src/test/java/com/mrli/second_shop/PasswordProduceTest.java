package com.mrli.second_shop;

import com.mrli.second_shop.security.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordProduceTest {

    // @Test
    public void createPassword(){
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    // @Test
    public void testJwt(){
        String token = JwtUtils.createJwtToken("admin",10);
        Claims claims = JwtUtils.parseJWT(token);
        System.out.println(claims.get("id"));
        System.out.println(claims.getId());
        System.out.println(claims.getIssuer());

    }


}
