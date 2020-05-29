package com.mrli.second_shop.vo.admin;

import com.mrli.second_shop.entity.ShsBackendCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类树
 */
@Data
@ApiModel(value="CategoryTreeVo对象", description="商品分类树")
public class CategoryTreeVo extends ShsBackendCategory {

    @ApiModelProperty("子商品分类树")
    List<CategoryTreeVo> children = new ArrayList<>();

}
