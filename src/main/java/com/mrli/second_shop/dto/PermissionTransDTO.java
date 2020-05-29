package com.mrli.second_shop.dto;

import com.mrli.second_shop.entity.ShsBackendPermission;
import com.mrli.second_shop.vo.admin.PermissonTreeVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将ShsBackendPermission列表转为PermissonTreeVo树
 */
public class PermissionTransDTO {

    public static List<PermissonTreeVo> toTree(List<ShsBackendPermission> list){

        List<PermissonTreeVo> tree = new ArrayList<>();

        list.forEach(permission -> {
            PermissonTreeVo dto = new PermissonTreeVo();
            BeanUtils.copyProperties(permission,dto);
            if("0".equals(permission.getPsLevel())){
                tree.add(dto);
            }else if("1".equals(permission.getPsLevel())){
                tree.forEach(node -> {
                    if(node.getPsId().intValue() == permission.getPsPid().intValue()){
                        node.getChildren().add(dto);
                    }
                });
            }else {
                tree.forEach(node -> {
                    node.getChildren().forEach(snode ->{
                        if(snode.getPsId().intValue() == permission.getPsPid().intValue()){
                            snode.getChildren().add(dto);
                        }
                    });
                });
            }
        });

        return tree;
    }
}
