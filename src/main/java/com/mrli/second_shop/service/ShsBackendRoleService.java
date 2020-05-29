package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.vo.admin.RoleListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-01-20
 */
public interface ShsBackendRoleService extends IService<ShsBackendRole> {

    /**
     * 获取角色及其对应的权限
     * @return
     */
    List<RoleListVo> getRoleWithPermission();

    /**
     * 删除 角色，并删除关联关系
     * @param id
     * @return
     */
    boolean deleteById(Integer id);

    /**
     * 根据角色ID和权限ID删除关联关系，递归删除子权限关联
     * @param roleId
     * @param psId
     * @return
     */
    boolean deletePermissionByRoleRel(Integer roleId, Integer psId);

    /**
     * 为角色授权
     * @param roleId
     * @param keys
     * @return
     */
    boolean allotRight(Integer roleId, List<Integer> keys);
}
