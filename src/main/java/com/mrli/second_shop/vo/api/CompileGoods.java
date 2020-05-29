package com.mrli.second_shop.vo.api;

import com.mrli.second_shop.entity.ShsFrontUser;
import lombok.Data;

import java.util.List;

@Data
public class CompileGoods extends FrontGoods {

    List<GoodsAttr> goodsAttrList;

    ShsFrontUser user;
}
