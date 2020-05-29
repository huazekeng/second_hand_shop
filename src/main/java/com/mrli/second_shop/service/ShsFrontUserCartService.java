package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsFrontUserCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.vo.api.CartVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-03-01
 */
public interface ShsFrontUserCartService extends IService<ShsFrontUserCart> {

    /**
     * 添加到购物车
     * @param id
     * @param param
     * @return
     */
    CartVo addCart(Integer id, Map<String, Object> param);

    /**
     * 获取购物车
     * @param id
     * @return
     */
    CartVo getCart(Integer id);

    /**
     * 修改购物车
     * @param id
     * @param goodsId
     * @param param
     * @return
     */
    CartVo updateCart(Integer id, Integer goodsId, Map<String, Object> param);

    /**
     * 全选
     * @param b
     * @return
     */
    CartVo selectAll(boolean b, Integer userId);

    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    CartVo deleteGoods(Integer goodsId, Integer userId);
}
