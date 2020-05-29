package com.mrli.second_shop.vo.admin;

import com.mrli.second_shop.entity.ShsBackendPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限树
 */
@ApiModel(value="PermissonTreeVo对象", description="权限树")
@Data
public class PermissonTreeVo extends ShsBackendPermission {

    @ApiModelProperty("子权限树")
    List<PermissonTreeVo> children = new ArrayList<>();


}
