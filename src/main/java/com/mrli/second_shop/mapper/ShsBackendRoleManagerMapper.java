package com.mrli.second_shop.mapper;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRoleManager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendRoleManagerMapper extends BaseMapper<ShsBackendRoleManager> {

    /**
     * 获取用户角色
     * @param username
     * @return
     */
    List<ShsBackendRole> getRolesByUser(@Param("username")String username);
}
