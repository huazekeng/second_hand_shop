package com.mrli.second_shop.entity;

import java.math.BigDecimal;
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
 * 订单表
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsFrontOrder对象", description="订单表")
public class ShsFrontOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty(value = "下订单会员id")
    private Integer userId;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "支付方式  0未支付 1支付宝  2微信  3银行卡")
    private String orderPay;

    @ApiModelProperty(value = "订单是否已经发货")
    private String isSend;

    @ApiModelProperty(value = "支付宝交易流水号码")
    private String tradeNo;

    @ApiModelProperty(value = "发票抬头 个人 公司")
    private String orderFapiaoTitle;

    @ApiModelProperty(value = "公司名称")
    private String orderFapiaoCompany;

    @ApiModelProperty(value = "发票内容")
    private String orderFapiaoContent;

    @ApiModelProperty(value = "consignee收货人地址")
    private String consigneeAddr;

    @ApiModelProperty(value = "订单状态： 0未付款、1已付款")
    private String payStatus;

    @ApiModelProperty(value = "记录生成时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "记录修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableLogic
    private Integer isDeleted;

    @Version
    private Integer version;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;
}
