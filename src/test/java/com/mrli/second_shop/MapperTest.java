package com.mrli.second_shop;

import com.mrli.second_shop.mapper.ShsBackendRoleMapper;
import com.mrli.second_shop.vo.admin.RoleListVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class MapperTest {

    // @Autowired
    private ShsBackendRoleMapper shsBackendRoleMapper;

    // @Test
    public void setShsBackendRoleMapperTest(){
        List<RoleListVo> roleListVos = shsBackendRoleMapper.roleListWithPermission();
        for (RoleListVo role : roleListVos) {
            System.out.println(role.getRoleName());
            System.out.println(role.getChildren().size());
        }

    }

}
