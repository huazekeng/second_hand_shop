package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendPermissionService extends IService<ShsBackendPermission> {

    /**
     * 根据用户获取前端菜单
     * @param username
     * @return
     */
    List<ShsBackendPermission> getMenuByUser(String username);


    /**
     *  获取角色权限
     * @param roleId
     * @return
     */
    List<ShsBackendPermission> listByRoleId(Integer roleId);

    /**
     * 获取子权限列表
     * @param psId
     */
    List<ShsBackendPermission> getChildrenList(Integer psId);
}
