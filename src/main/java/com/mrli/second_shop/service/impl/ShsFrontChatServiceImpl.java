package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.entity.ShsFrontChat;
import com.mrli.second_shop.mapper.ShsFrontChatMapper;
import com.mrli.second_shop.service.ShsFrontChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-03-08
 */
@Service
public class ShsFrontChatServiceImpl extends ServiceImpl<ShsFrontChatMapper, ShsFrontChat> implements ShsFrontChatService {

    @Override
    public List<ShsFrontChat> lastChats(String username) {
        return this.baseMapper.lastChats(username);
    }
}
