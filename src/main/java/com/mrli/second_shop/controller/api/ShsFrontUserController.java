package com.mrli.second_shop.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontConsignee;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.security.JwtUtils;
import com.mrli.second_shop.service.ShsFrontConsigneeService;
import com.mrli.second_shop.service.ShsFrontUserService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-24
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户控制器")
@Slf4j
public class ShsFrontUserController {

    @Autowired
    private ShsFrontUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ShsFrontConsigneeService consigneeService;


    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public R login(@ApiParam("登录参数 username password") @RequestBody Map<String,String> param) {
        String username = param.get("username");
        String password = param.get("password");
        ShsFrontUser user = userService.getByName(username);
        if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return R.error().message("用户名或密码错误");
        }
        //生成jwt
        String token =  JwtUtils.createJwtToken(username,jwtConfig.getExpiresSecond());
        //生成refresh_token
        String refreshToken = JwtUtils.createJwtToken(UUID.randomUUID().toString(),jwtConfig.getRefreshExpiresSecond());
        R result = R.ok().data("token",jwtConfig.getTokenHead() + " " + token).data("refresh_token",refreshToken).data("username", username);
        // 將token和userDetail緩存至Redis , 最長存放一天
        redisUtils.set(token, user, 1l , TimeUnit.DAYS);
        redisUtils.set(refreshToken,"api" + username,1l , TimeUnit.DAYS);
        return result;
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录接口")
    public R logout(HttpServletRequest request, @ApiParam("refresh_token") @RequestBody Map<String,String> param) {
        String refresh_token = param.get("refreshToken");
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        redisUtils.remove(refresh_token, authToken);
        return R.ok();
    }

    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public R register(@RequestBody Map<String,String> param) {
        String username = param.get("username");
        String passwrod = param.get("password");
        String email = param.get("email");
        ShsFrontUser user = userService.getByEmail(email);
        if (user !=null ){
            return R.error().message("该邮箱已经注册");
        }
        user = new ShsFrontUser();
        user.setUserEmail(email);
        user.setPassword(passwordEncoder.encode(passwrod));
        user.setUsername(username);
        boolean success = userService.save(user);
        return success ? R.ok() : R.error();
    }
    @GetMapping()
    @ApiOperation("获取用户信息接口")
    public R info(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        user = userService.getByName(user.getUsername());
        return R.ok().data("data", user);
    }

    @GetMapping("/name_exist")
    @ApiOperation("获取用户信息接口")
    public R nameExist(@RequestParam("username") String username){
        ShsFrontUser user = userService.getByName(username);
        return R.ok().data("flag", user==null ? false : true);
    }

    @PostMapping()
    @ApiOperation("编辑接口")
    public R info(@RequestBody ShsFrontUser user){
        Boolean success = userService.saveOrUpdate(user);
        return success ? R.ok(): R.error();
    }

    @PostMapping("/address")
    @ApiOperation("添加地址接口")
    public R addressAdd(HttpServletRequest request, @RequestBody ShsFrontConsignee consigness){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        consigness.setUserId(user.getId());
        consigness.setDetailAddress(consigness.getReceiverProvince() + " " + consigness.getReceiverAddress());
        Boolean success = consigneeService.save(consigness);
        return success ? R.ok(): R.error();
    }


    @GetMapping("/address")
    @ApiOperation("地址列表接口")
    public R addressList(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        QueryWrapper<ShsFrontConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        List<ShsFrontConsignee> list = consigneeService.list(queryWrapper);
        return R.ok().data("list", list);
    }

    @PutMapping("/address")
    @ApiOperation("地址修改")
    public R addressEdit(@RequestBody ShsFrontConsignee consigness){
        consigness.setDetailAddress(consigness.getReceiverProvince() + " " + consigness.getReceiverAddress());
        Boolean success = consigneeService.saveOrUpdate(consigness);
        return success ? R.ok(): R.error();
    }

    @DeleteMapping("/address/{id}")
    @ApiOperation("地址修改")
    public R addressDelete(@PathVariable("id") Integer id){
        Boolean success = consigneeService.removeById(id);
        return success ? R.ok(): R.error();
    }

    @ApiOperation("用戶列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        Page<ShsFrontUser> pageQuery =  userService.pageQuery(page,limit,query);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }
}

