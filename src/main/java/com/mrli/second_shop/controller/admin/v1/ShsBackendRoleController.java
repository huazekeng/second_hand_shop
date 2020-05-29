package com.mrli.second_shop.controller.admin.v1;


import com.mrli.second_shop.dto.PermissionTransDTO;
import com.mrli.second_shop.entity.ShsBackendPermission;
import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.service.ShsBackendPermissionService;
import com.mrli.second_shop.service.ShsBackendRoleService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.admin.PermissonTreeVo;
import com.mrli.second_shop.vo.R;
import com.mrli.second_shop.vo.admin.RoleListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-01-20
 */
@RequestMapping("/{version}/admin/backend-role")
@RestController
@Api(tags = "角色控制器")
@ApiVersion
@Slf4j
public class ShsBackendRoleController {

    @Autowired
    private ShsBackendRoleService roleService;

    @Autowired
    private ShsBackendPermissionService permissionService;


    @ApiOperation("获取角色列表")
    @GetMapping()
    public R list(){
        List<RoleListVo> voList = roleService.getRoleWithPermission();
        return R.ok().data("data",voList);
    }

    @ApiOperation("添加角色")
    @PostMapping()
    public R add(@RequestBody ShsBackendRole role){
        boolean success = roleService.save(role);
        return success ? R.ok() : R.error();
    }

    @ApiOperation("修改角色详情")
    @PutMapping()
    public R update(@RequestBody ShsBackendRole role){
        boolean success = roleService.saveOrUpdate(role);
        return success ? R.ok() : R.error();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    @Transactional
    public R delete(@PathVariable("id")@ApiParam("角色ID") Integer id){
        boolean success = roleService.deleteById(id);
        return success ? R.ok() : R.error();
    }

    @ApiOperation("取消角色授权")
    @DeleteMapping("/cancel_right")
    public R cancelRight(@RequestParam("roleId")@ApiParam("角色ID") Integer roleId,
                         @RequestParam("psId")@ApiParam("权限ID") Integer psId){

        boolean success = roleService.deletePermissionByRoleRel(roleId, psId);
        if(success){
            List<ShsBackendPermission> voList = permissionService.listByRoleId(roleId);
            List<PermissonTreeVo> ptree = PermissionTransDTO.toTree(voList);
            return R.ok().data("data",ptree);
        }
        return R.error();
    }

    @ApiOperation("为角色授权")
    @PutMapping("/allot_right/{roleId}")
    public R allotRight(@PathVariable("roleId")@ApiParam("角色ID") Integer roleId,
                        @RequestBody Map<String,Object> params){

        List<Integer> keys = (List<Integer>)params.get("keys");
        boolean success = roleService.allotRight(roleId, keys);
//        if(success){
//            List<ShsBackendPermission> voList = permissionService.listByRoleId(roleId);
//            List<PermissonTreeVo> ptree = PermissionTransDTO.toTree(voList);
//            return R.ok().data("data",ptree);
//        }
        return success ? R.ok() : R.error();
    }
}

