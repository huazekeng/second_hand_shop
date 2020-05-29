package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
public interface ShsBackendAttributeService extends IService<ShsBackendAttribute> {

    /**
     * 根据分类和类型获取参数列表
     * @param param
     * @return
     */
    List<ShsBackendAttribute> listByCatAndType(Integer catId,String type);
}
