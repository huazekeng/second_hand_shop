package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author HZK
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsFrontUser对象", description="会员表")
public class ShsFrontUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "qq官方唯一编号信息")
    private String qqOpenId;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    @ApiModelProperty(value = "新用户注册邮件激活唯一校验码")
    private String userEmailCode;

    @ApiModelProperty(value = "新用户是否已经通过邮箱激活帐号")
    private String isActive;

    @ApiModelProperty(value = "性别")
    private String userSex;

    @ApiModelProperty(value = "qq")
    private String userQq;

    @ApiModelProperty(value = "手机")
    private String userTel;

    @ApiModelProperty(value = "学历")
    private String userXueli;

    @ApiModelProperty(value = "爱好")
    private String userHobby;

    @ApiModelProperty(value = "简介")
    private String userIntroduce;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "专业班级")
    private String school;

    @ApiModelProperty(value = "信用度")
    private Integer confidence;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "地址")
    private String address;

}
