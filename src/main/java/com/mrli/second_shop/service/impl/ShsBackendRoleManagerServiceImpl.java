package com.mrli.second_shop.service.impl;

import com.mrli.second_shop.entity.ShsBackendRoleManager;
import com.mrli.second_shop.mapper.ShsBackendRoleManagerMapper;
import com.mrli.second_shop.service.ShsBackendRoleManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Service
public class ShsBackendRoleManagerServiceImpl extends ServiceImpl<ShsBackendRoleManagerMapper, ShsBackendRoleManager> implements ShsBackendRoleManagerService {

    @Override
    public Boolean removeByUserId(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("m_id",id);
        return this.removeByMap(param);
    }

    @Override
    public boolean removeByRoleId(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("r_id",id);
        return this.removeByMap(param);
    }
}
