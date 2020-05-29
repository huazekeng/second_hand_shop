package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsFrontReplay对象", description="")
public class ShsFrontReplay implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "回复者")
    private String username;

    @ApiModelProperty(value = "回复的帖子")
    private Integer forumId;

    @ApiModelProperty(value = "回复的内容")
    private String content;

    @ApiModelProperty(value = "第几楼")
    private Integer floor;

    @ApiModelProperty(value = "点赞数")
    private Integer agreementCount;

    @ApiModelProperty(value = "点赞列表")
    private String agreementList;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
}
