package com.mrli.second_shop.controller.admin.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.dto.PermissionTransDTO;
import com.mrli.second_shop.entity.ShsBackendPermission;
import com.mrli.second_shop.service.ShsBackendPermissionService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.admin.PermissonTreeVo;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@RestController
@RequestMapping("/{version}/admin/backend-permission")
@Api(tags = "权限控制器")
@ApiVersion
@Slf4j
public class ShsBackendPermissionController {

    @Autowired
    private ShsBackendPermissionService backendPermissionService;

    @ApiOperation("获取前端页面菜单")
    @GetMapping("/menu")
    public R menus(Principal principal){
        String username = principal.getName();
        log.info(username);
        List<ShsBackendPermission> list = backendPermissionService.getMenuByUser(username);
        List<PermissonTreeVo> voList = PermissionTransDTO.toTree(list);
        return R.ok().data("data",voList);
    }

    @ApiOperation("获取权限树")
    @GetMapping()
    public R list(Principal principal){
        QueryWrapper<ShsBackendPermission> query = new QueryWrapper<>();
        query.orderByAsc("ps_level","ps_id");
        List<ShsBackendPermission> list = backendPermissionService.list(query);
        List<PermissonTreeVo> voList = PermissionTransDTO.toTree(list);
        return R.ok().data("data",voList);
    }


}

