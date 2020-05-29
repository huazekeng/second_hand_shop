package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontRequire;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.form.SearchForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-03-13
 */
public interface ShsFrontRequireService extends IService<ShsFrontRequire> {

    Page<ShsFrontRequire> pageQuery(int page, int limit, String query, String username);

    Page<ShsFrontRequire> pageQuery(SearchForm search);
}
