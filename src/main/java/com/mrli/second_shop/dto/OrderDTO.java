package com.mrli.second_shop.dto;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.entity.ShsFrontConsignee;
import com.mrli.second_shop.entity.ShsFrontOrder;
import com.mrli.second_shop.entity.ShsFrontOrderGoods;
import com.mrli.second_shop.service.ShsFrontOrderGoodsService;
import com.mrli.second_shop.vo.api.OrderVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDTO {

    @Autowired
    private ShsFrontOrderGoodsService orderGoodsService;

    public List<OrderVo> toVo(List<ShsFrontOrder> olist){
        List<OrderVo> volist = new ArrayList<>();
        olist.forEach(order -> {
            OrderVo vo = new OrderVo();
            vo.setOrderNo(order.getOrderNumber());
            vo.setPayment(order.getOrderPrice());
            vo.setPaymentType(Integer.parseInt(order.getOrderPay()));
            vo.setPaymentTypeDesc("在线支付");
            vo.setStatus(Integer.parseInt(order.getPayStatus()));
            vo.setCreateTime(DateFormatUtils.format(order.getGmtCreate(),"yyyy-MM-dd hh:mm:ss"));
            String addressInfo = order.getConsigneeAddr();
            ShsFrontConsignee shsFrontConsignee = JSONObject.parseObject(addressInfo, ShsFrontConsignee.class);
            vo.setShippingVo(shsFrontConsignee);
            QueryWrapper<ShsFrontOrderGoods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("order_no", order.getOrderNumber());
            List<ShsFrontOrderGoods> list = orderGoodsService.list(goodsQueryWrapper);
            vo.setPostage(list.size());
            vo.setOrderItemVoList(list);
            vo.setImageHost(list.get(0).getProductImage());
            vo.setReceiverName(shsFrontConsignee.getReceiverName());
            volist.add(vo);
        });
        return volist;
    }
}
