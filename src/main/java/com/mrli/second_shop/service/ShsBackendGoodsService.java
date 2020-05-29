package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.form.GoodsAddForm;
import com.mrli.second_shop.form.SearchForm;
import com.mrli.second_shop.vo.api.FrontGoods;
import com.mrli.second_shop.vo.api.MenuItem;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-14
 */
public interface ShsBackendGoodsService extends IService<ShsBackendGoods> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @param s
     * @param query
     * @return
     */
    Page<ShsBackendGoods> pageQuery(int page, int limit, String s, String query);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    ShsBackendGoods getByName(String name);

    /**
     * 添加商品
     * @param goods
     * @return
     */
    boolean saveGoods(GoodsAddForm goods, String username);

    /**
     * 获取广告菜单商品
     * @return
     */
    List<FrontGoods> menuGoods();

    /**
     * 获取广告菜单商品
     * @return
     */
    List<MenuItem> getMenuList();

    Page<ShsBackendGoods> pageQuery(SearchForm search);
}
