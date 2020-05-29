package com.mrli.second_shop.vo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("购物车VO")
public class CartProductVo {
    @ApiModelProperty("购物车ID")
    private Integer id;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("商品ID")
    private Integer productId;
    @ApiModelProperty("商品加入购物车的数量")
    private Integer quantity;
    @ApiModelProperty("商品名称")
    private String  productName;
    @ApiModelProperty("商品副标题")
    private String productSubtitle;
    @ApiModelProperty("商品主图")
    private String productMainImage;
    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;
    @ApiModelProperty("商品状态")
    private Integer productStatus;
    @ApiModelProperty("购物车商品总价格")
    private BigDecimal productTotalPrice;
    @ApiModelProperty("商品库存")
    private Integer productStock;
    @ApiModelProperty("是否选中")
    private Boolean productSelected;
}
