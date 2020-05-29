package com.mrli.second_shop.vo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("购物车对象")
public class CartVo {

    List<CartProductVo> cartProductVoList = new ArrayList<>();
    Boolean selectedAll;
    BigDecimal cartTotalPrice;
    Integer cartTotalQuantity;

}
