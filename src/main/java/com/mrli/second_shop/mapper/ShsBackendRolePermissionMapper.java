package com.mrli.second_shop.mapper;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRolePermission;
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
public interface ShsBackendRolePermissionMapper extends BaseMapper<ShsBackendRolePermission> {

    List<ShsBackendRole> getRolesByUrl(@Param("url") String url);

    /**
     *  獲取用戶角色，需要處理url帶‘*’的情況
     * @param url
     * @param username
     * @return
     */
    List<ShsBackendRole> getRolesByUrlAndUser(@Param("url")String url, @Param("username") String username, @Param("method") String method);
}
