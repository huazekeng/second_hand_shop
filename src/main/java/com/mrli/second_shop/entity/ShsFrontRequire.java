package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

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
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsFrontRequire对象", description="")
public class ShsFrontRequire implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发布人")
    private String username;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "最低接受价格")
    private Integer min;

    @ApiModelProperty(value = "最高接受价格")
    private Integer max;

    @ApiModelProperty(value = "0下架 1 上架")
    private Integer status;

    @ApiModelProperty(value = "添加商品时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

}
