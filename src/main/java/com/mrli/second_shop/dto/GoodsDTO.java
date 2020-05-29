package com.mrli.second_shop.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.entity.*;
import com.mrli.second_shop.service.ShsBackendAttributeService;
import com.mrli.second_shop.service.ShsBackendGoodsAttrService;
import com.mrli.second_shop.service.ShsBackendGoodsPicsService;
import com.mrli.second_shop.service.ShsFrontUserService;
import com.mrli.second_shop.util.BeanUtil;
import com.mrli.second_shop.vo.api.CompileGoods;
import com.mrli.second_shop.vo.api.FrontGoods;
import com.mrli.second_shop.vo.api.GoodsAttr;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsDTO {

    @Autowired
    private ShsBackendGoodsPicsService picsService;

    @Autowired
    private ShsBackendAttributeService attributeService;

    @Autowired
    private ShsBackendGoodsAttrService goodsAttrService;

    @Autowired
    private ShsFrontUserService userService;

    public List<FrontGoods> toDto(List<ShsBackendGoods> list) {

        List<FrontGoods> goods = new ArrayList<>();
        list.forEach(item -> {
            FrontGoods good = new FrontGoods();
            BeanUtils.copyProperties(item, good);
            QueryWrapper<ShsBackendGoodsPics> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("goods_id", item.getGoodsId());
            List<ShsBackendGoodsPics> list1 = picsService.list(queryWrapper);
            good.setPics(list1);
            good.setGoodsSmallLogo(list1!=null  && list1.size()> 0 ? list1.get(0).getPicsSma() : "");
            goods.add(good);
        });

        return goods;
    }

    public FrontGoods toDto(ShsBackendGoods item) {
        FrontGoods good = new FrontGoods();
        BeanUtils.copyProperties(item, good);
        QueryWrapper<ShsBackendGoodsPics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", item.getGoodsId());
        good.setPics(picsService.list(queryWrapper));
        return good;
    }

    public CompileGoods toCompileDto(ShsBackendGoods item) {
        CompileGoods good = new CompileGoods();
        BeanUtils.copyProperties(item, good);
        QueryWrapper<ShsBackendGoodsPics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", item.getGoodsId());
        good.setPics(picsService.list(queryWrapper));

        QueryWrapper<ShsBackendGoodsAttr> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("goods_id", item.getGoodsId());
        List<GoodsAttr> goodsAttrs = new ArrayList<>();
        List<ShsBackendGoodsAttr> list = goodsAttrService.list(queryWrapper2);
        list.forEach(ga -> {
            GoodsAttr gattr = new GoodsAttr();
            BeanUtils.copyProperties(ga, gattr);
            ShsBackendAttribute attr = attributeService.getById(ga.getAttrId());
            gattr.setType(attr.getAttrSel());
            gattr.setAttrName(attr.getAttrName());
            goodsAttrs.add(gattr);
        });
        good.setGoodsAttrList(goodsAttrs);
        QueryWrapper<ShsFrontUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", item.getCreateUser());
        ShsFrontUser user = userService.getOne(userQueryWrapper);
        good.setUser(user);
        return good;
    }
}
