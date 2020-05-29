package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsFrontChat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-03-08
 */
public interface ShsFrontChatService extends IService<ShsFrontChat> {


    /**
     * 获取每个聊天最后一条记录
     * @param username
     */
    List<ShsFrontChat> lastChats(String username);
}
