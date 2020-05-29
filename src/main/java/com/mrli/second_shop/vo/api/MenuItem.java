package com.mrli.second_shop.vo.api;

import com.mrli.second_shop.dto.GoodsDTO;
import com.mrli.second_shop.entity.ShsBackendCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="MenuItem", description="MenuItem对象")
public class MenuItem extends ShsBackendCategory {

    List<FrontGoods> goods = new ArrayList<>();
}
