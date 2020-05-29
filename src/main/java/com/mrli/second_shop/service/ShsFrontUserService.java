package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-24
 */
public interface ShsFrontUserService extends IService<ShsFrontUser> {

    ShsFrontUser getByName(String username);

    ShsFrontUser getByEmail(String email);

    Page<ShsFrontUser> pageQuery(int page, int limit, String query);
}
