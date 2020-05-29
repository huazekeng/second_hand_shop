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
 * 收货人表
 * </p>
 *
 * @author HZK
 * @since 2020-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsFrontConsignee对象", description="收货人表")
public class ShsFrontConsignee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "cgn_id", type = IdType.AUTO)
    private Integer cgnId;

    @ApiModelProperty(value = "会员id")
    private Integer userId;

    @ApiModelProperty(value = "收货人名称")
    private String receiverName;

    @ApiModelProperty(value = "收货人地址")
    private String detailAddress;

    @ApiModelProperty(value = "收货人电话")
    private String receivePhone;

    @ApiModelProperty(value = "邮编")
    private String receiverZip;

    @ApiModelProperty(value = "省市区")
    private String receiverProvince;

    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;


}
