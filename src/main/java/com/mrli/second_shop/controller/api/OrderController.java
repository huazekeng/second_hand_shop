package com.mrli.second_shop.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.dto.OrderDTO;
import com.mrli.second_shop.entity.ShsFrontOrder;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontOrderService;
import com.mrli.second_shop.util.OrderCodeFactory;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import com.mrli.second_shop.vo.api.OrderVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "订单控制器")
@Slf4j
public class OrderController {

    @Autowired
    private ShsFrontOrderService orderService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private OrderDTO orderDTO;

    @PostMapping()
    public R createOrder(HttpServletRequest request, @RequestBody Map<String, Integer> param) {
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        String orderNumber = OrderCodeFactory.getOrderCode(null);
        Integer addrId = param.get("shippingId");
        orderService.save(addrId, orderNumber, user.getId());
        return R.ok().data("orderNo", orderNumber);
    }

    @GetMapping("/{orderNo}")
    public R getOrderVo(@PathVariable("orderNo")String orderNo){
        OrderVo vo = orderService.getVoByNo(orderNo);
        return R.ok().data("vo", vo);
    }

    @GetMapping()
    public R list(HttpServletRequest request, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        Page<ShsFrontOrder> page = orderService.pageQuery(user.getId(), pageNum ,pageSize);
        return R.ok().data("total",page.getTotal()).data("list",orderDTO.toVo(page.getRecords())).data("hasNextPage", page.hasNext());
    }

}
