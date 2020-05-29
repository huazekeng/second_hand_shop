package com.mrli.second_shop.vo.api;

import com.mrli.second_shop.entity.ShsFrontConsignee;
import com.mrli.second_shop.entity.ShsFrontOrderGoods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderVo {

    private String orderNo;

    private BigDecimal payment; //总额

    private Integer paymentType; //支付类型

    private String paymentTypeDesc = "在线支付";

    private Integer status; //订单状态

    private Integer postage = 0;

    private String statusDesc; //状态描述

    private String paymentTime;// 支付时间

    private String sendTime;// 送货时间

    private String endTime;// 订单结束时间

    private String closeTime;//订单关闭时间

    private String createTime; // 订单创建时间

    private List<ShsFrontOrderGoods> orderItemVoList;

    private ShsFrontConsignee shippingVo;

    private String receiverName;

    private String imageHost;
}
