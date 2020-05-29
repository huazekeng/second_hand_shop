package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.vo.api.OrderVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
public interface ShsFrontOrderService extends IService<ShsFrontOrder> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @param query
     * @return
     */
    Page<ShsFrontOrder> pageQuery(int page, int limit, String query);

    void save(Integer addrId, String orderNumber, Integer id);

    OrderVo getVoByNo(String orderNo);

    Page<ShsFrontOrder> pageQuery(int userid, int page, int limit);
}
