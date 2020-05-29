package com.mrli.second_shop.vo.api;

import com.mrli.second_shop.entity.ShsBackendGoodsAttr;
import lombok.Data;

@Data
public class GoodsAttr extends ShsBackendGoodsAttr {

    private String type;

    private String attrName;

}
