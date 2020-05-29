package com.mrli.second_shop.vo.admin;

import com.mrli.second_shop.entity.ShsBackendRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色及其对应的权限
 */
@Data
@ApiModel(value="RoleListVo对象", description="角色及其对应的权限")
public class RoleListVo extends ShsBackendRole {

    @ApiModelProperty("权限树")
    List<PermissonTreeVo> children = new ArrayList<>();

}
