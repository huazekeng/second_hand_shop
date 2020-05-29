package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendCategory;
import com.mrli.second_shop.mapper.ShsBackendCategoryMapper;
import com.mrli.second_shop.service.ShsBackendCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.vo.admin.CategoryTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
@Service
public class ShsBackendCategoryServiceImpl extends ServiceImpl<ShsBackendCategoryMapper, ShsBackendCategory> implements ShsBackendCategoryService {

    @Override
    public Page<ShsBackendCategory> pageQuery(Integer page, Integer limit, String query) {
        Page<ShsBackendCategory> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsBackendCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cat_level", 0);
        queryWrapper.orderByAsc("order_level","cat_id");
        this.page(pageQuery,queryWrapper);
        return pageQuery;
    }

    @Override
    public Page<CategoryTreeVo> pageLevelQuery(Integer page, Integer limit, Integer level) {
        List<CategoryTreeVo> list = new ArrayList<>();
        List<ShsBackendCategory> ls = new ArrayList<>();
        Page<ShsBackendCategory> shsBackendCategoryPage = null;
        if(page!=-1 && limit!=-1) {
            shsBackendCategoryPage = this.pageQuery(page, limit, null);
            ls = shsBackendCategoryPage.getRecords();
        }else {
            QueryWrapper<ShsBackendCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cat_level", 0);
            queryWrapper.orderByAsc("order_level","cat_id");
            ls = this.baseMapper.selectList(queryWrapper);
        }
        Page<CategoryTreeVo> pageResult = new Page<>();
//        for(ShsBackendCategory category : shsBackendCategoryPage.getRecords()) { // 遍历一级分类列表
//            CategoryTreeVo vo = new CategoryTreeVo();
//            BeanUtils.copyProperties(category,vo);
//            list.add(vo); // 一级分类
//            if ( level > 1 ) {
//                List<ShsBackendCategory> children = this.getChildren(category.getCatId());// 二级分类列表
//                for(ShsBackendCategory category1 : children) {
//                    CategoryTreeVo vo1 = new CategoryTreeVo();
//                    BeanUtils.copyProperties(category1,vo1);
//                    vo.getChildren().add(vo1);
//                    if ( level > 2 ) {
//                        List<ShsBackendCategory> children2 = this.getChildren(category1.getCatId()); // 三级分类列表
//                        for(ShsBackendCategory category2 : children2){
//                            CategoryTreeVo vo2 = new CategoryTreeVo();
//                            BeanUtils.copyProperties(category2,vo2);
//                            vo1.getChildren().add(vo2);
//                        }
//                    }
//                }
//            }
//        }
        ls.forEach( category -> {
            CategoryTreeVo vo = new CategoryTreeVo();
            BeanUtils.copyProperties(category,vo);
            list.add(vo); // 一级分类
            if ( level > 1 ) {
                List<ShsBackendCategory> children = this.getChildren(category.getCatId());// 二级分类列表
                children.forEach(category1 -> {
                    CategoryTreeVo vo1 = new CategoryTreeVo();
                    BeanUtils.copyProperties(category1,vo1);
                    vo.getChildren().add(vo1);
                    if ( level > 2 ) {
                        List<ShsBackendCategory> children2 = this.getChildren(category1.getCatId()); // 三级分类列表
                        children2.forEach( category2 -> {
                            CategoryTreeVo vo2 = new CategoryTreeVo();
                            BeanUtils.copyProperties(category2,vo2);
                            vo1.getChildren().add(vo2);
                            vo2.setChildren(null);
                        });
                    }else{
                        vo1.setChildren(null);
                    }
                });
            }

        });
        pageResult.setTotal(shsBackendCategoryPage == null ? ls.size() : shsBackendCategoryPage.getTotal() );
        pageResult.setRecords(list);
        return pageResult;
    }

    @Override
    public List<ShsBackendCategory> getChildren(Integer catId) {
        // Map<String,Object> param = new HashMap<>();
        //param.put("cat_pid",catId);
        QueryWrapper<ShsBackendCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_level");
        queryWrapper.eq("cat_pid",catId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean deleteById(Integer id) {
        ShsBackendCategory category = this.getById(id);
        category.setIsDeleted(1);
        Boolean success = this.saveOrUpdate(category);
        return success;
    }
}
