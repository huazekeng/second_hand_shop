package com.mrli.second_shop.form;

import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.mrli.second_shop.entity.ShsBackendGoodsAttr;
import com.mrli.second_shop.validator.IsNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ApiModel("商品添加表单对象")
@Data
public class GoodsAddForm {

    @ApiModelProperty("商品名称")
    @NotNull(message="商品名称不能为空")
    private String goodsName;

    @ApiModelProperty("商品分类")
    @NotEmpty(message="商品分类不能为空")
    private Integer[] catIds;

    @ApiModelProperty("商品简介")
    private String goodsIntroduce;

    @ApiModelProperty("商品价格")
    @IsNumber(message = "商品价格必须为数字")
    private BigDecimal goodsPrice;

    @ApiModelProperty("商品数量")
    @IsNumber(message = "商品数量必须为数字")
    private Integer goodsNumber;

    @ApiModelProperty("商品重量")
    @IsNumber(message = "商品重量必须为数字")
    private Integer goodsWeight;

    @ApiModelProperty("商品属性/参数")
    private List<ShsBackendGoodsAttr> goodsAttr;

    @ApiModelProperty("商品图片")
    @NotEmpty(message="商品图片不能为空")
    private String[] goodsPic;

    @ApiModelProperty("商品备注")
    private String goodsBackup;

    @ApiModelProperty("轮播图")
    private String slidePic;

    @ApiModelProperty("视频")
    private String videoUrl;

    @ApiModelProperty("banner图")
    private String bannerPic;

    @ApiModelProperty("副标题")
    private String subTitle;
}
