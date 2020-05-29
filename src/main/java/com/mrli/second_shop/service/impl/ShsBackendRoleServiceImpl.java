package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.dto.PermissionTransDTO;
import com.mrli.second_shop.entity.ShsBackendPermission;
import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRolePermission;
import com.mrli.second_shop.mapper.ShsBackendRoleMapper;
import com.mrli.second_shop.service.ShsBackendPermissionService;
import com.mrli.second_shop.service.ShsBackendRoleManagerService;
import com.mrli.second_shop.service.ShsBackendRolePermissionService;
import com.mrli.second_shop.service.ShsBackendRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.vo.admin.PermissonTreeVo;
import com.mrli.second_shop.vo.admin.RoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-01-20
 */
@Service
@Transactional
public class ShsBackendRoleServiceImpl extends ServiceImpl<ShsBackendRoleMapper, ShsBackendRole> implements ShsBackendRoleService {


    @Autowired
    private ShsBackendPermissionService  permissionService;

    @Autowired
    private ShsBackendRoleManagerService roleManagerService;

    @Autowired
    private ShsBackendRolePermissionService rolePermissionService;

    @Override
    public List<RoleListVo> getRoleWithPermission() {
        // return this.baseMapper.roleListWithPermission();
        List<RoleListVo> roleVoList = this.baseMapper.roleVoList();
        roleVoList.forEach(role ->{
            List<ShsBackendPermission> list = permissionService.listByRoleId(role.getRoleId());
            List<PermissonTreeVo> ptree = PermissionTransDTO.toTree(list);
            role.setChildren(ptree);
        });
        return roleVoList;
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean success = this.removeById(id);
        // 删除关联关系
        roleManagerService.removeByRoleId(id);
        rolePermissionService.removeByRoleId(id);
        return success;
    }

    @Override
    public boolean deletePermissionByRoleRel(Integer roleId, Integer psId) {
        boolean success = rolePermissionService.deletePermissionAndRoleRel(roleId,psId);
        if(success) {
            ShsBackendPermission permission = permissionService.getById(psId);
            if (permission.getPsLevel().equals("0") || permission.getPsLevel().equals("1")) {
                List<ShsBackendPermission> childrenList = permissionService.getChildrenList(psId);
                for(ShsBackendPermission p : childrenList) {
                    if (permission.getPsLevel().equals("0")) {
                        List<ShsBackendPermission> childrenList1 = permissionService.getChildrenList(p.getPsId());
                        for (ShsBackendPermission p1 : childrenList1) {
                            rolePermissionService.deletePermissionAndRoleRel(roleId, p1.getPsId()); // 删除三级关联
                        }
                    }
                    rolePermissionService.deletePermissionAndRoleRel(roleId, p.getPsId()); // 删除二级关联
                }
            }

        }
        return success;
    }

    @Override
    public boolean allotRight(Integer roleId, List<Integer> keys) {
        // 删除旧权限关联
        boolean success = rolePermissionService.removeByRoleId(roleId);
        List<ShsBackendRolePermission> list = new ArrayList<>();
        // 添加新权限关联
        if (keys !=null)
            for(Integer key : keys){
                ShsBackendRolePermission sp = new ShsBackendRolePermission();
                sp.setRId(roleId);
                sp.setPId(key);
                list.add(sp);
            }

        if (list.size() > 0) {
            success = success && rolePermissionService.saveBatch(list);
        }

        return success;
    }
}
