package com.mrli.second_shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.*;
import com.mrli.second_shop.mapper.ShsFrontOrderMapper;
import com.mrli.second_shop.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.vo.api.CartProductVo;
import com.mrli.second_shop.vo.api.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.CharArrayReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
@Service
@Transactional
public class ShsFrontOrderServiceImpl extends ServiceImpl<ShsFrontOrderMapper, ShsFrontOrder> implements ShsFrontOrderService {


    @Autowired
    private ShsFrontUserCartService cartService;

    @Autowired
    private ShsFrontConsigneeService consigneeService;

    @Autowired
    private ShsBackendGoodsService goodsService;

    @Autowired
    private ShsFrontOrderGoodsService orderGoodsService;

    @Autowired
    private ShsBackendGoodsPicsService picsService;

    @Override
    public Page<ShsFrontOrder> pageQuery(int page, int limit, String query) {
        Page<ShsFrontOrder> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsFrontOrder> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("order_number",query);
        }
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }

    @Override
    public void save(Integer addrId, String orderNumber, Integer user_id) {
        QueryWrapper<ShsFrontUserCart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", user_id);
        ShsFrontUserCart cart = cartService.getOne(cartQueryWrapper);
        String carInfo = cart.getCartInfo();
        List<CartProductVo> cartProductVos = JSONObject.parseArray(carInfo, CartProductVo.class);
        List<CartProductVo> temp1 = new ArrayList<>();
        List<CartProductVo> temp2 = new ArrayList<>();
        cartProductVos.forEach(item -> {
            if(item.getProductSelected()) {
                temp1.add(item);
            }else {
                temp2.add(item);
            }
        });
        // 更新购物车
        carInfo = JSONObject.toJSONString(temp2);
        cart.setCartInfo(carInfo);


        ShsFrontConsignee address = consigneeService.getById(addrId);
        String addressInfo = JSONObject.toJSONString(address);


        List<ShsFrontOrderGoods> orderGoods = new ArrayList<>();
        List<ShsBackendGoods> goods = new ArrayList<>();
        BigDecimal total = new BigDecimal(0.00);
        for(CartProductVo item : temp1){
            Integer productId = item.getProductId();
            QueryWrapper<ShsBackendGoodsPics> picsQueryWrapper = new QueryWrapper<>();
            picsQueryWrapper.eq("goods_id" , productId);
            picsQueryWrapper.orderByAsc("pics_id");
            ShsBackendGoodsPics shsBackendGoodsPics = picsService.list(picsQueryWrapper).get(0);
            String picsSma = shsBackendGoodsPics.getPicsSma();

            ShsFrontOrderGoods orderGood = new ShsFrontOrderGoods();
            orderGood.setGoodsPrice(item.getProductPrice());
            orderGood.setOrderNo(orderNumber);
            orderGood.setProductImage(picsSma);
            orderGood.setProductName(item.getProductName());
            orderGood.setQuantity(item.getQuantity());
            orderGood.setTotalPrice(item.getProductTotalPrice());
            orderGood.setProductId(productId);

            ShsBackendGoods good = goodsService.getById(productId);
            good.setGoodsNumber(good.getGoodsNumber() - item.getQuantity());

            goods.add(good);
            orderGoods.add(orderGood);
            total = total.add(item.getProductTotalPrice());
        };
        ShsFrontOrder order = new ShsFrontOrder();
        order.setOrderNumber(orderNumber);
        order.setUserId(user_id);
        order.setOrderPrice(total);
        order.setConsigneeAddr(addressInfo);

        // 更新购物车
        cartService.saveOrUpdate(cart);
        // 订单
        this.save(order);
        // 订单商品
        orderGoodsService.saveBatch(orderGoods);
        // 商品
        goodsService.saveOrUpdateBatch(goods);
    }

    @Override
    public OrderVo getVoByNo(String orderNo) {
        OrderVo vo = new OrderVo();
        QueryWrapper<ShsFrontOrder> orderQueryWrapper = new QueryWrapper<>() ;
        orderQueryWrapper.eq("order_number", orderNo);
        ShsFrontOrder order = this.getOne(orderQueryWrapper);
        vo.setOrderNo(orderNo);
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
        return vo;
    }


    @Override
    public Page<ShsFrontOrder> pageQuery(int userid, int page, int limit) {
        Page<ShsFrontOrder> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsFrontOrder> orderQueryWrapper = new QueryWrapper<>() ;
        orderQueryWrapper.eq("user_id", userid);
        this.page(pageQuery, orderQueryWrapper);
        return pageQuery;
    }
}
