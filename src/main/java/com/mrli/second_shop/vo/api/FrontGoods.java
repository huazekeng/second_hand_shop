package com.mrli.second_shop.vo.api;

import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.entity.ShsBackendGoodsPics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="FrontGoods", description="商品对象对象")
public class FrontGoods extends ShsBackendGoods {

    @ApiModelProperty("图片")
    public List<ShsBackendGoodsPics> pics;

}
