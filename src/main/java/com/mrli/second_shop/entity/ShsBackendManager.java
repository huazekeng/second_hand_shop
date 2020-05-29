package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mrli.second_shop.serializer.CustomDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsBackendManager对象", description="管理员表")
public class ShsBackendManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "mg_id", type = IdType.AUTO)
    private Integer mgId;

    @ApiModelProperty(value = "名称")
    private String mgName;

    @ApiModelProperty(value = "密码")
    private String mgPwd;

    @ApiModelProperty(value = "注册时间")
    @TableField(fill = FieldFill.INSERT)
    // @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss") 表单
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date gmtCreate;

    private String mgMobile;

    private String mgEmail;

    @ApiModelProperty(value = "1：表示删除 0:表示未删除")
    //@TableLogic
    private Integer isDeleted;


}
