package com.mrli.second_shop.mapper;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrli.second_shop.vo.admin.RoleListVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HZK
 * @since 2020-01-20
 */
public interface ShsBackendRoleMapper extends BaseMapper<ShsBackendRole> {


    /**
     * 获取角色机器权限（Tree）
     * @return
     */
    public List<RoleListVo> roleListWithPermission();

    /**
     * 获取角色列表
     * @return
     */
    List<RoleListVo> roleVoList();
}
