package com.mrli.second_shop.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.config.AlipayConfig;
import com.mrli.second_shop.entity.ShsFrontOrder;
import com.mrli.second_shop.service.ShsFrontOrderService;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@Api(tags = "订单控制器")
@Slf4j
public class PayController {

    @Autowired
    private ShsFrontOrderService orderService;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfig alipayConfig;

    @PostMapping()
    public R pay(@RequestBody Map<String, Object> param) {
        String orderNo = param.get("orderId").toString();
        String orderName = "二手商城订单";
        QueryWrapper<ShsFrontOrder> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("order_number", orderNo);
        ShsFrontOrder one = orderService.getOne(queryWrapper);
        BigDecimal orderPrice = one.getOrderPrice();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderNo + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orderPrice.doubleValue() + "," +
                "    \"subject\":\"" + orderName + "\"," +
                "    \"body\":\"" + orderName + "\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok().data("content", form);
    }

    @RequestMapping("/alnotify")
    public void alnotify(@RequestParam Map<String,String> param) throws AlipayApiException {
        // System.out.println(JSONObject.toJSONString(param));
        boolean signVerified = AlipaySignature.rsaCheckV1(param, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(),alipayConfig.getSign_type());//调用SDK验证签名
        String orderNo = param.get("out_trade_no");
        QueryWrapper<ShsFrontOrder> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("order_number", orderNo);
        ShsFrontOrder one = orderService.getOne(queryWrapper);
        one.setPayStatus("1");
        orderService.saveOrUpdate(one);
        if(signVerified){
            // TODO 验签成功则继续业务操作，最后在response中返回success
            log.info("支付成功");
        }else{
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("failure");
        }
    }
}
