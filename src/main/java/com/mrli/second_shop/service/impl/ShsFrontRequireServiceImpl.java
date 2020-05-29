package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontRequire;
import com.mrli.second_shop.form.SearchForm;
import com.mrli.second_shop.mapper.ShsFrontRequireMapper;
import com.mrli.second_shop.service.ShsFrontRequireService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-03-13
 */
@Service
public class ShsFrontRequireServiceImpl extends ServiceImpl<ShsFrontRequireMapper, ShsFrontRequire> implements ShsFrontRequireService {

    @Override
    public Page<ShsFrontRequire> pageQuery(int page, int limit, String query, String username) {
        Page<ShsFrontRequire> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsFrontRequire> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.and(wrapper -> wrapper.like("title", query).or().like("description", query));
        }
        if(!StringUtils.isEmpty(username)){
            queryWrapper.eq("username",username);
        }
        queryWrapper.orderByDesc("gmt_create");
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }

    @Override
    public Page<ShsFrontRequire> pageQuery(SearchForm search) {
        Page<ShsFrontRequire> page = new Page<>();
        page.setCurrent(search.getPage());
        page.setSize(search.getSize());
        QueryWrapper<ShsFrontRequire> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(search.getKey())) {
            queryWrapper.and(wrapper -> wrapper.like("title", search.getKey()).or().like("description", search.getKey()));
        }
        if (!StringUtils.isBlank(search.getPriceGt())) {
            queryWrapper.le("max", search.getPriceGt());
        }
        if (!StringUtils.isBlank(search.getPriceLte())) {
            queryWrapper.ge("min", search.getPriceLte());
        }
        if (search.getSort() == 2){
            queryWrapper.orderByAsc("min");
        }
        if (search.getSort() == 3) {
            queryWrapper.orderByDesc("min");
        }
        this.page(page,queryWrapper);
        return page;
    }
}
