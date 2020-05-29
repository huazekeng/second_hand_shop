package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.mapper.ShsFrontUserMapper;
import com.mrli.second_shop.service.ShsFrontUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-24
 */
@Service
public class ShsFrontUserServiceImpl extends ServiceImpl<ShsFrontUserMapper, ShsFrontUser> implements ShsFrontUserService {

    public ShsFrontUser getByName(String username) {
        QueryWrapper<ShsFrontUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username    );
        return this.getOne(queryWrapper);
    }

    @Override
    public ShsFrontUser getByEmail(String email) {
        QueryWrapper<ShsFrontUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", email    );
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<ShsFrontUser> pageQuery(int page, int limit, String query) {
        Page<ShsFrontUser> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsFrontUser> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("username",query);
        }
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }

}
