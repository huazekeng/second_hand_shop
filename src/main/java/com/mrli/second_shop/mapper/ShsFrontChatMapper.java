package com.mrli.second_shop.mapper;

import com.mrli.second_shop.entity.ShsFrontChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HZK
 * @since 2020-03-08
 */
public interface ShsFrontChatMapper extends BaseMapper<ShsFrontChat> {

    List<ShsFrontChat> lastChats(@Param("username") String username);
}
