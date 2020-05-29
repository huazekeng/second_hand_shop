package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.mrli.second_shop.mapper.ShsBackendAttributeMapper;
import com.mrli.second_shop.service.ShsBackendAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 属性表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
@Service
public class ShsBackendAttributeServiceImpl extends ServiceImpl<ShsBackendAttributeMapper, ShsBackendAttribute> implements ShsBackendAttributeService {

    @Override
    public List<ShsBackendAttribute> listByCatAndType(Integer catId,String type) {
        Map<String,Object> param = new HashMap<>();
        param.put("cat_id",catId);
        param.put("attr_sel",type);
        Collection<ShsBackendAttribute> shsBackendAttributes = this.listByMap(param);
        ShsBackendAttribute[] sas = new ShsBackendAttribute[shsBackendAttributes.size()];
        shsBackendAttributes.toArray(sas);
        return Arrays.asList(sas);
    }
}
