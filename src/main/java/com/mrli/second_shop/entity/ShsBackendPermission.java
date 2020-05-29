package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsBackendPermission对象", description="权限表")
public class ShsBackendPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ps_id", type = IdType.AUTO)
    private Integer psId;

    @ApiModelProperty(value = "权限名称")
    private String psName;

    @ApiModelProperty(value = "父id")
    private Integer psPid;

    @ApiModelProperty(value = "后台接口")
    private String psUrl;

    @ApiModelProperty(value = "权限等级")
    private String psLevel;

    @ApiModelProperty(value = "前端頁面鏈接")
    private String frontUrl;

    @ApiModelProperty(value = "请求方法")
    private String method;

}
