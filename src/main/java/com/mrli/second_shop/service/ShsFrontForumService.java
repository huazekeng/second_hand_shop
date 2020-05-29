package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontForum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-03-14
 */
public interface ShsFrontForumService extends IService<ShsFrontForum> {

    Page<ShsFrontForum> pageQuery(int page, int limit, String query, String username);
}
