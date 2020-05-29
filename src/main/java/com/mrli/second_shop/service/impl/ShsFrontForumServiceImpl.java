package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontForum;
import com.mrli.second_shop.mapper.ShsFrontForumMapper;
import com.mrli.second_shop.service.ShsFrontForumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-03-14
 */
@Service
public class ShsFrontForumServiceImpl extends ServiceImpl<ShsFrontForumMapper, ShsFrontForum> implements ShsFrontForumService {

    @Override
    public Page<ShsFrontForum> pageQuery(int page, int limit, String query, String username) {
        Page<ShsFrontForum> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsFrontForum> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.and(wrapper -> wrapper.like("title", query).or().like("content", query));
        }
        if(!StringUtils.isEmpty(username)){
            queryWrapper.eq("username", username);
        }
        queryWrapper.orderByDesc("gmt_create");
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }
}
