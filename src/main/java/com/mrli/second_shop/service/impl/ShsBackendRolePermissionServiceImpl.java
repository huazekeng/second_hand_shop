package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRolePermission;
import com.mrli.second_shop.mapper.ShsBackendRolePermissionMapper;
import com.mrli.second_shop.service.ShsBackendRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Service
public class ShsBackendRolePermissionServiceImpl extends ServiceImpl<ShsBackendRolePermissionMapper, ShsBackendRolePermission> implements ShsBackendRolePermissionService {

    @Override
    public List<ShsBackendRole> getRolesByUrl(String url) {
        return this.baseMapper.getRolesByUrl(url);
    }

    @Override
    public List<ShsBackendRole> getRolesByUrlAndUser(String url, String username,String method) {
        return this.baseMapper.getRolesByUrlAndUser(url,username,method);
    }

    @Override
    public boolean removeByRoleId(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("r_id",id);
        return this.removeByMap(param);
    }

    @Override
    public boolean deletePermissionAndRoleRel(Integer roleId, Integer psId) {
        Map<String, Object> param = new HashMap<>();
        param.put("r_id",roleId);
        param.put("p_id",psId);
        return this.removeByMap(param);
    }
}
