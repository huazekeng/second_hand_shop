package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.entity.ShsBackendPermission;
import com.mrli.second_shop.mapper.ShsBackendPermissionMapper;
import com.mrli.second_shop.service.ShsBackendPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Service
public class ShsBackendPermissionServiceImpl extends ServiceImpl<ShsBackendPermissionMapper, ShsBackendPermission> implements ShsBackendPermissionService {

    @Override
    public List<ShsBackendPermission> getMenuByUser(String username) {
        return this.baseMapper.getPermissionByUser(username);
    }

    @Override
    public List<ShsBackendPermission> listByRoleId(Integer roleId) {
        return this.baseMapper.listByRoleId(roleId);
    }

    @Override
    public List<ShsBackendPermission> getChildrenList(Integer psId) {
        return this.baseMapper.getChildrenList(psId);
    }
}
