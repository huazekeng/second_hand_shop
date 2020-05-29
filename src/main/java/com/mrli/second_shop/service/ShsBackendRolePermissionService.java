package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendRolePermissionService extends IService<ShsBackendRolePermission> {

    /**
     * 根据权限获取角色
     * @return  角色集合
     */
    List<ShsBackendRole> getRolesByUrl(String url);

    /**
     * 通过请求URL，Method ，用户名获取角色
     * @param requestUrl
     * @param username
     * @param method
     * @return
     */
    List<ShsBackendRole> getRolesByUrlAndUser(String requestUrl, String username, String method);

    /**
     * 根据角色删除角色权限关联
     * @param id
     * @return
     */
    boolean removeByRoleId(Integer id);

    /**
     * 删除角色和权限关联
     * @param roleId
     * @param psId
     * @return
     */
    boolean deletePermissionAndRoleRel(Integer roleId, Integer psId);
}
