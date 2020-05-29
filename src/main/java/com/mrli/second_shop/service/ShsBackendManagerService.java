package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendManager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.entity.ShsBackendRole;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
public interface ShsBackendManagerService extends IService<ShsBackendManager> {

    /**
     * 根据用户名获取角色
     * @param username 用户名
     * @return 角色集合
     */
    List<ShsBackendRole> getRole(String username);

    /**
     *  根据用户名获取用户对象
     * @param username 用户名
     * @return 用户对象
     */
    ShsBackendManager getByName(String username);

    /**
     * 分頁查詢
     * @param page 頁碼
     * @param limit 條目數
     * @param query 查詢參數
     * @return
     */
    Page<ShsBackendManager> pageQuery(int page, int limit, String query);

    /**
     * 删除用户，并删除角色的关联关系
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /**
     * 根据用户ID获取角色ID集合
     * @param id
     * @return
     */
    List<Integer> getRoleIds(Integer id);

    /**
     * 为用户分配角色
     * @param mgId
     * @param keys
     * @return
     */
    boolean allotRoles(Integer mgId, List<Integer> keys);
}
