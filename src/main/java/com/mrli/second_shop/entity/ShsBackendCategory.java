package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsBackendCategory对象", description="")
public class ShsBackendCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类唯一ID")
    @TableId(value = "cat_id", type = IdType.AUTO)
    private Integer catId;

    @ApiModelProperty(value = "分类名称")
    private String catName;

    @ApiModelProperty(value = "分类父ID")
    private Integer catPid;

    @ApiModelProperty(value = "分类层级 0: 顶级 1:二级 2:三级")
    private Integer catLevel;

    @ApiModelProperty(value = "是否删除 1为删除")
    // @TableLogic
    private Integer isDeleted;

    private String catIcon;

    private String catSrc;

    private Integer orderLevel;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
