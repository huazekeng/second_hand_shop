package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.dto.GoodsDTO;
import com.mrli.second_shop.entity.ShsBackendCategory;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.entity.ShsBackendGoodsAttr;
import com.mrli.second_shop.entity.ShsBackendGoodsPics;
import com.mrli.second_shop.form.GoodsAddForm;
import com.mrli.second_shop.form.SearchForm;
import com.mrli.second_shop.mapper.ShsBackendGoodsMapper;
import com.mrli.second_shop.service.ShsBackendCategoryService;
import com.mrli.second_shop.service.ShsBackendGoodsAttrService;
import com.mrli.second_shop.service.ShsBackendGoodsPicsService;
import com.mrli.second_shop.service.ShsBackendGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.util.PictureUtil;
import com.mrli.second_shop.vo.api.FrontGoods;
import com.mrli.second_shop.vo.api.MenuItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.Cacheable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-14
 */
@Service
@Transactional
@Slf4j
public class ShsBackendGoodsServiceImpl extends ServiceImpl<ShsBackendGoodsMapper, ShsBackendGoods> implements ShsBackendGoodsService {

    @Autowired
    private ShsBackendGoodsAttrService goodsAttrService;

    @Autowired
    private ShsBackendGoodsPicsService picsService;

    @Autowired
    private ShsBackendCategoryService categoryService;

    @Autowired
    private GoodsDTO goodsDTO;

    @Override
    public Page<ShsBackendGoods> pageQuery(int page, int limit, String query, String username) {
        Page<ShsBackendGoods> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("goods_name",query);
        }
        if(!StringUtils.isEmpty(username)){
            queryWrapper.eq("create_user",username);
        }
        queryWrapper.orderByDesc("gmt_create");
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }

    @Override
    public ShsBackendGoods getByName(String name) {
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_name",name);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean saveGoods(GoodsAddForm form, String username) {
        try {
            // 商品基本属性赋值
            ShsBackendGoods goods = new ShsBackendGoods();

            // 图片视频链接处理
            String bannerUrl = PictureUtil.copyFile(form.getBannerPic());
            String slideUrl = PictureUtil.copyFile(form.getSlidePic());
            String videoUrl = PictureUtil.copyFile(form.getVideoUrl());

            goods.setBackup(form.getGoodsBackup());
            goods.setCatId(form.getCatIds()[2]);
            goods.setCatOneId(form.getCatIds()[0]);
            goods.setCatThreeId(form.getCatIds()[2]);
            goods.setCatTwoId(form.getCatIds()[1]);
            goods.setCreateUser(username);
            goods.setGoodsBigLogo("");
            goods.setGoodsSmallLogo("");
            goods.setGoodsIntroduce(form.getGoodsIntroduce());
            goods.setGoodsName(form.getGoodsName()) ;
            goods.setGoodsPrice(form.getGoodsPrice());
            goods.setGoodsWeight(form.getGoodsWeight());
            goods.setGoodsNumber(form.getGoodsNumber());
            goods.setSlidePic(slideUrl);
            goods.setVideoUrl(videoUrl);
            goods.setBannerPic(bannerUrl);
            goods.setSubTitle(form.getSubTitle());

            Boolean success = this.save(goods);
            // 商品属（参数）赋值
            List<ShsBackendGoodsAttr> goodsAttr = form.getGoodsAttr();
            goodsAttr.forEach(attr -> {
                attr.setGoodsId(goods.getGoodsId());
            });
            success = goodsAttrService.saveBatch(goodsAttr) && success;

            // 商品图片赋值
            String[] goodsPic = form.getGoodsPic();
            List<ShsBackendGoodsPics> pics = new ArrayList<>();
            for (String pic : goodsPic) {
                pic = PictureUtil.class.getResource(pic).getPath();
                String url1 = PictureUtil.zoomBySize(pic, 800 , 800);
                String url2 = PictureUtil.zoomBySize(pic, 400 , 400);
                String url3 = PictureUtil.zoomBySize(pic, 200 , 200);
                ShsBackendGoodsPics temp = new ShsBackendGoodsPics();
                temp.setGoodsId(goods.getGoodsId());
                temp.setPicsBig(url1);
                temp.setPicsMid(url2);
                temp.setPicsSma(url3);
                pics.add(temp);
            }
            success = picsService.saveBatch(pics) && success;

            return success;
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    @CachePut(value="goods", key="category")
    public List<FrontGoods> menuGoods() {
        log.info("不从缓存拿");
        List<FrontGoods> list = new ArrayList<>();
        List<Integer> catids = new ArrayList<>();
        catids.add(188);
        catids.add(187);
        catids.add(1487);
        catids.add(191);
        catids.add(192);
        catids.add(190);
        catids.forEach(catid -> {
            QueryWrapper<ShsBackendGoods> goodsQueryWrapper = new QueryWrapper<>();
            Page<ShsBackendGoods> pageQuery = new Page<>();
            pageQuery.setCurrent(1);
            pageQuery.setSize(1);
            Page<ShsBackendGoodsPics> picPageQuery = new Page<>();
            picPageQuery.setCurrent(1);
            picPageQuery.setSize(1);
            goodsQueryWrapper.eq("status", 1);
            goodsQueryWrapper.orderByDesc("gmt_create");
            QueryWrapper<ShsBackendGoodsPics> picsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("cat_id", catid);
            ShsBackendGoods goods =  this.page(pageQuery,goodsQueryWrapper).getRecords().get(0);
            FrontGoods fg = new FrontGoods();
            BeanUtils.copyProperties(goods,fg);
            picsQueryWrapper.eq("goods_id", goods.getGoodsId());
//            ShsBackendGoodsPics pics = picsService.page(picPageQuery, picsQueryWrapper).getRecords().get(0);
//            fg.setBigPic(pics.getPicsBig());
//            fg.setMidPic(pics.getPicsMid());
//            fg.setSmallPic(pics.getPicsSma());
            list.add(fg);
        });
        return list;
    }

    @Override
    public List<MenuItem> getMenuList() {
        Page<ShsBackendCategory> page = new Page<>();
        QueryWrapper<ShsBackendCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_level");
        queryWrapper.eq("cat_level", 0);
        page.setCurrent(1);
        page.setSize(8);
        IPage<ShsBackendCategory> pageQuery = categoryService.page(page, queryWrapper);
        List<ShsBackendCategory> records = pageQuery.getRecords();
        List<MenuItem> mList = new ArrayList<>();
        records.forEach(item -> {
            MenuItem menuItem = new MenuItem() ;
            BeanUtils.copyProperties(item, menuItem);
            QueryWrapper<ShsBackendGoods> goodsQuery = new QueryWrapper<>();
            goodsQuery.eq("cat_one_id", item.getCatId());
            goodsQuery.orderByDesc("gmt_create");
            Page<ShsBackendGoods> gpage = new Page<>();
            gpage.setCurrent(1);
            gpage.setSize(24);
            IPage<ShsBackendGoods> goodsPage = this.page(gpage, goodsQuery);
            List<ShsBackendGoods> goodsRecords = goodsPage.getRecords();
            menuItem.setGoods(goodsDTO.toDto(goodsRecords));
            mList.add(menuItem);
        });
        return mList;
    }

    @Override
    public Page<ShsBackendGoods> pageQuery(SearchForm search) {
        Page<ShsBackendGoods> page = new Page<>();
        page.setCurrent(search.getPage());
        page.setSize(search.getSize());
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(search.getKey())) {
            queryWrapper.like("goods_name", search.getKey());
        }
        if (!StringUtils.isBlank(search.getPriceGt())) {
            queryWrapper.ge("goods_price", search.getPriceGt());
        }
        if (!StringUtils.isBlank(search.getPriceLte())) {
            queryWrapper.le("goods_price", search.getPriceLte());
        }
        if (search.getSort() == 2){
            queryWrapper.orderByAsc("goods_price");
        }
        if (search.getSort() == 3) {
            queryWrapper.orderByDesc("goods_price");
        }
        this.page(page,queryWrapper);
        return page;
    }
}
