package com.mrli.second_shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.dto.GoodsDTO;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.entity.ShsFrontUserCart;
import com.mrli.second_shop.mapper.ShsBackendGoodsMapper;
import com.mrli.second_shop.mapper.ShsFrontUserCartMapper;
import com.mrli.second_shop.service.ShsFrontUserCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.vo.api.CartProductVo;
import com.mrli.second_shop.vo.api.CartVo;
import com.mrli.second_shop.vo.api.FrontGoods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-03-01
 */
@Service
public class ShsFrontUserCartServiceImpl extends ServiceImpl<ShsFrontUserCartMapper, ShsFrontUserCart> implements ShsFrontUserCartService {

    @Autowired
    private ShsBackendGoodsMapper goodsMapper;

    @Autowired
    private GoodsDTO goodsDTO;

    @Override
    public CartVo addCart(Integer userid, Map<String, Object> param) {
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        Integer goodsId = Integer.parseInt(param.get("productId").toString());
        queryWrapper.eq("user_id", userid);
        ShsFrontUserCart one = this.getOne(queryWrapper);
        List<CartProductVo> cartProductVos = new ArrayList<>();
        Boolean succcess = false;
        if (one!=null) { // 用户还没有购物车
            String cartInfo = one.getCartInfo();
            if (!StringUtils.isBlank(cartInfo)) { // 有购物车有商品
                cartProductVos = JSONObject.parseArray(cartInfo, CartProductVo.class);
                boolean flag = false;
                for (CartProductVo vo: cartProductVos) {
                    if(vo.getProductId().intValue() == goodsId.intValue()) {
                        flag = true;
                        vo.setQuantity(vo.getQuantity() + 1);
                        vo.setProductSelected(true);
                        vo.setProductTotalPrice(vo.getProductTotalPrice().add(vo.getProductPrice()));
                    }
                }

                if(!flag) { // 购物车没有要添加的商品
                    CartProductVo vo1 = createVo(userid,goodsId, cartProductVos.size() + 1);
                    cartProductVos.add(vo1);
                }
                cartInfo = JSONObject.toJSONString(cartProductVos);
            } else {
                cartProductVos = new ArrayList<>();
                CartProductVo vo1 = createVo(userid, goodsId, 1);
                cartProductVos.add(vo1);
                cartInfo = JSONObject.toJSONString(cartProductVos);
            }
            one.setCartInfo(cartInfo);
            this.saveOrUpdate(one);
        } else {
            ShsFrontUserCart cart = new ShsFrontUserCart();
            cart.setUserId(userid);
            cartProductVos = new ArrayList<>();
            CartProductVo vo1 = createVo(userid, goodsId, 1);
            cartProductVos.add(vo1);
            String cartInfo = JSONObject.toJSONString(cartProductVos);
            cart.setCartInfo(cartInfo);
            this.save(cart);
        }
        CartVo vo = new CartVo();
        vo.setCartProductVoList(cartProductVos);
        fillDate(vo, cartProductVos);
        return vo;
    }

    @Override
    public CartVo updateCart(Integer userid, Integer goodsId, Map<String, Object> param) {
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        Integer quantity = Integer.parseInt(param.get("quantity").toString());
        Boolean selected = Boolean.parseBoolean(param.get("selected").toString());
        queryWrapper.eq("user_id", userid);
        ShsFrontUserCart one = this.getOne(queryWrapper);
        String cartInfo = one.getCartInfo();
        List<CartProductVo> cartProductVos = JSONObject.parseArray(cartInfo, CartProductVo.class);
        cartProductVos.forEach(item -> {
            if (item.getProductId().intValue() == goodsId.intValue()) {
                item.setQuantity(quantity);
                item.setProductSelected(selected);
            }
        });
        cartInfo = JSONObject.toJSONString(cartProductVos);
        one.setCartInfo(cartInfo);
        this.saveOrUpdate(one);
        CartVo vo = new CartVo();
        fillDate(vo, cartProductVos);
        vo.setCartProductVoList(cartProductVos);
        return vo;
    }

    @Override
    public CartVo selectAll(boolean selected, Integer userId) {
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        ShsFrontUserCart one = this.getOne(queryWrapper);
        String cartInfo = one.getCartInfo();
        List<CartProductVo> cartProductVos = JSONObject.parseArray(cartInfo, CartProductVo.class);
        cartProductVos.forEach(item -> {
            item.setProductSelected(selected);
        });
        cartInfo = JSONObject.toJSONString(cartProductVos);
        one.setCartInfo(cartInfo);
        this.saveOrUpdate(one);
        CartVo vo = new CartVo();
        fillDate(vo, cartProductVos);
        vo.setCartProductVoList(cartProductVos);
        return vo;
    }

    @Override
    public CartVo deleteGoods(Integer goodsId, Integer userId) {
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        ShsFrontUserCart one = this.getOne(queryWrapper);
        String cartInfo = one.getCartInfo();
        List<CartProductVo> cartProductVos = JSONObject.parseArray(cartInfo, CartProductVo.class);
        List<CartProductVo> cartProductVos2 = new ArrayList<>();
        cartProductVos.forEach(item -> {
            if (item.getProductId().intValue() != goodsId) {
                cartProductVos2.add(item);
            }
        });
        cartInfo = JSONObject.toJSONString(cartProductVos2);
        one.setCartInfo(cartInfo);
        this.saveOrUpdate(one);
        CartVo vo = new CartVo();
        fillDate(vo, cartProductVos2);
        vo.setCartProductVoList(cartProductVos2);
        return vo;
    }

    @Override
    public CartVo getCart(Integer id) {
        CartVo vo = new CartVo();
        QueryWrapper<ShsFrontUserCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        ShsFrontUserCart one = this.getOne(queryWrapper);
        String cartInfo = one.getCartInfo();
        List<CartProductVo> cartProductVos = JSONObject.parseArray(cartInfo, CartProductVo.class);
        fillDate(vo, cartProductVos);
        vo.setCartProductVoList(cartProductVos);
        return vo;
    }

    private void fillDate(CartVo vo, List<CartProductVo> cartProductVos) {
        Integer total = 0;
        BigDecimal totalPrice = new BigDecimal(0.00);
        Boolean selectAll = true;
        for (CartProductVo vo1:cartProductVos ) {
            total++;
            if (!vo1.getProductSelected()) {
                selectAll = false;
            }
            totalPrice = totalPrice.add(vo1.getProductTotalPrice());
        }
        vo.setCartTotalPrice(totalPrice);
        vo.setSelectedAll(selectAll);
        vo.setCartTotalQuantity(total);
    }

    private CartProductVo createVo(Integer userid, Integer goodsId, Integer cartId) {

        CartProductVo vo1 = new CartProductVo();
        ShsBackendGoods shsBackendGoods = goodsMapper.selectById(goodsId);
        FrontGoods frontGoods = goodsDTO.toDto(shsBackendGoods);
        vo1.setProductTotalPrice(shsBackendGoods.getGoodsPrice());
        vo1.setProductSelected(true);
        vo1.setQuantity(1);
        vo1.setProductId(goodsId);
        vo1.setProductMainImage(frontGoods.getPics().get(0).getPicsSma());
        vo1.setProductName(shsBackendGoods.getGoodsName());
        vo1.setProductPrice(shsBackendGoods.getGoodsPrice());
        vo1.setProductStatus(shsBackendGoods.getIsDeleted().equals(1) ? 1 : 0);
        vo1.setProductStock(shsBackendGoods.getGoodsNumber());;
        vo1.setProductSubtitle(shsBackendGoods.getSubTitle());
        vo1.setUserId(userid);
        vo1.setId(cartId);
        return vo1;
    }
}
