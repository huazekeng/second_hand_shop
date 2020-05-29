package com.mrli.second_shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.vo.admin.CategoryTreeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
public interface ShsBackendCategoryService extends IService<ShsBackendCategory> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @param o
     * @return
     */
    Page<ShsBackendCategory> pageQuery(Integer page, Integer limit, String query);

    /**
     * 按查询等级进行分页查询
     * @param page
     * @param limit
     * @param level
     * @return
     */
    Page<CategoryTreeVo> pageLevelQuery(Integer page, Integer limit, Integer level);

    /**
     * 获取子分类
     * @param catId
     * @return
     */
    List<ShsBackendCategory> getChildren(Integer catId);

    /**
     * 删除分类（逻辑状态为）
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);
}
