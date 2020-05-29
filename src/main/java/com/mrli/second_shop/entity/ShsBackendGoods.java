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
 * 商品表
 * </p>
 *
 * @author HZK
 * @since 2020-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsBackendGoods对象", description="商品表")
public class ShsBackendGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "商品重量")
    private Integer goodsWeight;

    @ApiModelProperty(value = "类型id")
    private Integer catId;

    @ApiModelProperty(value = "商品详情介绍")
    private String goodsIntroduce;

    @ApiModelProperty(value = "图片logo大图")
    private String goodsBigLogo;

    @ApiModelProperty(value = "图片logo小图")
    private String goodsSmallLogo;

    @ApiModelProperty(value = "0:正常  1:删除")
    @TableLogic
    private String isDeleted;

    @ApiModelProperty(value = "添加商品时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改商品时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "软删除标志字段")
    private Date deleteTime;

    @ApiModelProperty(value = "一级分类id")
    private Integer catOneId;

    @ApiModelProperty(value = "二级分类id")
    private Integer catTwoId;

    @ApiModelProperty(value = "三级分类id")
    private Integer catThreeId;

    @ApiModelProperty(value = "热卖数量")
    private Integer hotMumber;

    @ApiModelProperty(value = "是否促销")
    private Integer isPromote;

    @ApiModelProperty(value = "商品状态 0: 未通过 1: 审核通过")
    private Integer goodsState;

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "备注")
    private String backup;

    @ApiModelProperty(value = "是否展示Banner 0: 否 1: 是")
    private Integer isBanner;

    @ApiModelProperty(value = "创建者")
    private String bannerPic;

    @ApiModelProperty(value = "是否展示轮播图 0: 否 1: 是 ")
    private Integer isSlide;

    @ApiModelProperty(value = "创建者")
    private String slidePic;

    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "是否展示广告图 0: 否 1: 是 ")
    private Integer isAds;

    @ApiModelProperty(value = "创建者")
    private String adsPic;

    @ApiModelProperty(value = "0:下架 1：上架")
    private Integer status;

}
