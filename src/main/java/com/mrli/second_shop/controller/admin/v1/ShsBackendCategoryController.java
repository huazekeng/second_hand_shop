package com.mrli.second_shop.controller.admin.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.mrli.second_shop.entity.ShsBackendCategory;
import com.mrli.second_shop.service.ShsBackendAttributeService;
import com.mrli.second_shop.service.ShsBackendCategoryService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.admin.CategoryTreeVo;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/{version}/admin/backend-category")
@ApiVersion
@Api(tags = "商品分类控制器")
@Slf4j
public class ShsBackendCategoryController {

    @Autowired
    private ShsBackendCategoryService backendCategoryService;

    @GetMapping("/{page}/{limit}/{level}")
    @ApiOperation("分类列表")
    public R list(@ApiParam("页码")@PathVariable("page")Integer page,
                  @ApiParam("每页条目数")@PathVariable("limit")Integer limit,
                  @ApiParam("查询参数")@PathVariable("level")Integer level){

        Page<CategoryTreeVo> categoryTreeVoPage = backendCategoryService.pageLevelQuery(page, limit, level);

        return R.ok().data("total",categoryTreeVoPage.getTotal()).data("rows",categoryTreeVoPage.getRecords());
    }

    @PostMapping()
    @ApiOperation("添加分类")
    public R add(@RequestBody ShsBackendCategory category){
        Boolean success = backendCategoryService.save(category);
        return success ? R.ok() : R.error();
    }

    @PutMapping()
    @ApiOperation("修改分类")
    public R put(@RequestBody ShsBackendCategory category){
        Boolean success = backendCategoryService.saveOrUpdate(category);
        return success ? R.ok() : R.error();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    public R delete(@ApiParam("ID")@PathVariable("id")Integer id){
        Boolean success = backendCategoryService.deleteById(id);
        return success ? R.ok() : R.error();
    }

    @GetMapping("/{id}")
    @ApiOperation("删除分类")
    public R getInfo(@ApiParam("ID")@PathVariable("id")Integer id){
        ShsBackendCategory byId = backendCategoryService.getById(id);
        return R.ok().data("data", byId);
    }


}

