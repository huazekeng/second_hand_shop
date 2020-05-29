package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendManager;
import com.mrli.second_shop.entity.ShsBackendRole;
import com.mrli.second_shop.entity.ShsBackendRoleManager;
import com.mrli.second_shop.mapper.ShsBackendManagerMapper;
import com.mrli.second_shop.mapper.ShsBackendRoleManagerMapper;
import com.mrli.second_shop.service.ShsBackendManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.service.ShsBackendRoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-01-19
 */
@Service
@Transactional
public class ShsBackendManagerServiceImpl extends ServiceImpl<ShsBackendManagerMapper, ShsBackendManager> implements ShsBackendManagerService {

    @Autowired
    private ShsBackendRoleManagerService roleManagerService;

    @Autowired
    private ShsBackendRoleManagerMapper roleManagerMapper;

    @Override
    public List<ShsBackendRole> getRole(String username) {
        return roleManagerMapper.getRolesByUser(username);
    }

    @Override
    public ShsBackendManager getByName(String username) {
        QueryWrapper<ShsBackendManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mg_name",username);
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<ShsBackendManager> pageQuery(int page, int limit, String query) {
        Page<ShsBackendManager> pageQuery = new Page<>();
        pageQuery.setCurrent(page);
        pageQuery.setSize(limit);
        QueryWrapper<ShsBackendManager> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("mg_name",query);
        }
        this.page(pageQuery,queryWrapper);

        return pageQuery;
    }

    @Override
    public Boolean deleteById(Integer id) {
        Boolean success = this.removeById(id);
        roleManagerService.removeByUserId(id);
        return success;
    }

    @Override
    public List<Integer> getRoleIds(Integer id) {
        Map<String,Object> param = new HashMap<>();
        param.put("m_id",id);
        Collection<ShsBackendRoleManager> shsBackendRoleManagers = roleManagerService.listByMap(param);
        List<Integer> list = new ArrayList<>();
        shsBackendRoleManagers.forEach(m -> {
            list.add(m.getRId());
        });
        return list;
    }

    @Override
    public boolean allotRoles(Integer mgId, List<Integer> keys) {
        // 删除旧权限关联
        boolean success = roleManagerService.removeByUserId(mgId);
        List<ShsBackendRoleManager> list = new ArrayList<>();
        // 添加新权限关联
        if (keys !=null)
            for(Integer key : keys){
                ShsBackendRoleManager sm = new ShsBackendRoleManager();
                sm.setRId(key);
                sm.setMId(mgId);
                list.add(sm);
            }

        if (list.size() > 0) {
            success = success && roleManagerService.saveBatch(list);
        }

        return success;
    }
}
