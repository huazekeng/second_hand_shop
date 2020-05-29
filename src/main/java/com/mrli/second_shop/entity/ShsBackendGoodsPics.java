package com.mrli.second_shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品-相册关联表
 * </p>
 *
 * @author HZK
 * @since 2020-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShsBackendGoodsPics对象", description="商品-相册关联表")
public class ShsBackendGoodsPics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "pics_id", type = IdType.AUTO)
    private Integer picsId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "相册大图800*800")
    private String picsBig;

    @ApiModelProperty(value = "相册中图350*350")
    private String picsMid;

    @ApiModelProperty(value = "相册小图50*50")
    private String picsSma;


}
