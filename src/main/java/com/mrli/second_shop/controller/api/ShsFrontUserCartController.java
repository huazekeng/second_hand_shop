package com.mrli.second_shop.controller.api;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.entity.ShsFrontUserCart;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontUserCartService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import com.mrli.second_shop.vo.api.CartProductVo;
import com.mrli.second_shop.vo.api.CartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-03-01
 */
@RestController
@RequestMapping("/api/carts")
@Api(tags = "购物车控制器")
@Slf4j
public class ShsFrontUserCartController {

    @Autowired
    private ShsFrontUserCartService cartService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping()
    @ApiOperation("添加到购物车")
    public R addressAdd(HttpServletRequest request, @RequestBody Map<String,Object> param){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.addCart(user.getId(), param);
        return R.ok().data("data", vo);
    }

    @GetMapping()
    @ApiOperation("获取购物车")
    public R get(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.getCart(user.getId());
        return R.ok().data("data", vo);
    }





    @GetMapping("/products/sum")
    @ApiOperation("购物车大小")
    public R getCartSum(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        ShsFrontUserCart one = cartService.getOne(queryWrapper);
        String carInfo = one.getCartInfo();
        List<CartProductVo> cartProductVos = JSONArray.parseArray(carInfo, CartProductVo.class);

        return R.ok().data("data", cartProductVos.size());
    }

    @PutMapping("/{goodsId}")
    @ApiOperation("修改购物车")
    public R addressAdd(HttpServletRequest request, @RequestBody Map<String,Object> param, @PathVariable("goodsId")Integer goodsId){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.updateCart(user.getId(), goodsId, param);
        return R.ok().data("data", vo);
    }

    @PutMapping("/unSelectAll")
    @ApiOperation("修改购物车")
    public R unSelectAll(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.selectAll(false,user.getId());
        return R.ok().data("data", vo);
    }

    @PutMapping("/selectAll")
    @ApiOperation("修改购物车")
    public R selectAll(HttpServletRequest request){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.selectAll(true,user.getId());
        return R.ok().data("data", vo);
    }

    @DeleteMapping("/{goodsId}")
    @ApiOperation("修改购物车")
    public R selectAll(HttpServletRequest request, @PathVariable("goodsId")Integer goodsId){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        CartVo vo  = cartService.deleteGoods(goodsId, user.getId());
        return R.ok().data("data", vo);
    }

}

