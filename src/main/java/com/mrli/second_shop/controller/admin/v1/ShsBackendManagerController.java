package com.mrli.second_shop.controller.admin.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendManager;
import com.mrli.second_shop.service.ShsBackendManagerService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@RestController
@RequestMapping("/{version}/admin/backend-manager")
@ApiVersion
@Api(tags = "管理用戶控制器")
@Slf4j
public class ShsBackendManagerController {


    @Autowired
    private ShsBackendManagerService managerService;

    @ApiOperation("用戶列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        Page<ShsBackendManager> pageQuery =  managerService.pageQuery(page,limit,query);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @ApiOperation("更新用户")
    @PutMapping
    public R update(@ApiParam("用户对象")@RequestBody ShsBackendManager manager){
        boolean success = managerService.saveOrUpdate(manager);
        return success ? R.ok() : R.error();
    }

    @ApiOperation("添加用户")
    @PostMapping
    public R add(@ApiParam("用户对象")@RequestBody ShsBackendManager manager){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        manager.setMgPwd(passwordEncoder.encode(manager.getMgPwd()));
        boolean success = managerService.save(manager);
        return success ? R.ok() : R.error();
    }

    @ApiOperation("校验用户名")
    @GetMapping("/validName")
    public R validName(String name){
        ShsBackendManager byName = managerService.getByName(name);
        return byName == null ? R.ok().data("data",false) : R.ok().data("data",true);
    }

    @ApiOperation("校验用户名")
    @DeleteMapping("/{id}")
    public R delete(@ApiParam(value = "用户Id")@PathVariable(value = "id") Integer id){
        Boolean success = managerService.deleteById(id);
        return success ? R.ok().data("data",false) : R.ok().data("data",true);
    }

    @ApiOperation("获取用户角色")
    @GetMapping("/role/{id}")
    public R getUserRole(@ApiParam(value = "用户Id")@PathVariable(value = "id") Integer id){
        List<Integer> rIds = managerService.getRoleIds(id);
        return R.ok().data("data",rIds);
    }

    @ApiOperation("为角色授权")
    @PutMapping("/allot_role/{mgId}")
    public R allotRight(@PathVariable("mgId")@ApiParam("用户ID") Integer mgId,
                        @RequestBody Map<String,Object> params){

        List<Integer> keys = (List<Integer>)params.get("keys");
        boolean success = managerService.allotRoles(mgId, keys);
        return success ? R.ok() : R.error();
    }

}

