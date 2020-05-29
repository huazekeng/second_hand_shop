package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendRoleManager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendRoleManagerService extends IService<ShsBackendRoleManager> {

    /**
     * 根据用户删除用户角色关联
     * @param id
     * @return
     */
    Boolean removeByUserId(Integer id);

    /**
     * 根据角色删除用户角色关联
     * @param id
     * @return
     */
    boolean removeByRoleId(Integer id);
}
