package com.mrli.second_shop.mapper;

import com.mrli.second_shop.entity.ShsBackendPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrli.second_shop.vo.admin.PermissonTreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendPermissionMapper extends BaseMapper<ShsBackendPermission> {

    /**
     * 获取用户权限
     * @param username
     * @return
     */
    List<ShsBackendPermission> getPermissionByUser(@Param("username")String username);

    /**
     * 获取权限树（一级和二级权限）
     * @return
     */
    List<PermissonTreeVo> getPermissionTree();

    /**
     * 获取子权限树
     * @return
     */
    List<PermissonTreeVo> getChildren(@Param("ps_id")Integer pid);

    /**
     * 获取角色权限
     * @return
     */
    List<ShsBackendPermission> listByRoleId(@Param("role_id")Integer roleId);

    /**
     * 获取子权限列表
     * @param psId
     * @return
     */
    List<ShsBackendPermission> getChildrenList(@Param("ps_id")Integer psId);
}
