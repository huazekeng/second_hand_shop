package com.mrli.second_shop.dto;

import com.mrli.second_shop.entity.ShsBackendCategory;
import com.mrli.second_shop.vo.admin.CategoryTreeVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将ShsBackendCategory列表转为CategoryTransDTO树
 */
public class CategoryTransDTO {

    public static List<CategoryTreeVo> toTree(List<ShsBackendCategory> list){

        List<CategoryTreeVo> tree = new ArrayList<>();

        list.forEach(category -> {
            CategoryTreeVo dto = new CategoryTreeVo();
            BeanUtils.copyProperties(category,dto);
            if("0".equals(category.getCatLevel())){
                tree.add(dto);
            }else if("1".equals(category.getCatLevel())){
                tree.forEach(node -> {
                    if(node.getCatPid().intValue() == category.getCatPid().intValue()){
                        node.getChildren().add(dto);
                    }
                });
            }else {
                tree.forEach(node -> {
                    node.getChildren().forEach(snode ->{
                        if(snode.getCatPid().intValue() == category.getCatPid().intValue()){
                            snode.getChildren().add(dto);
                        }
                    });
                });
            }
        });

        return tree;
    }
}
