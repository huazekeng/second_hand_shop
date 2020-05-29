package com.mrli.second_shop.controller.admin.v1;


import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.mrli.second_shop.service.ShsBackendAttributeService;
import com.mrli.second_shop.version.ApiVersion;
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
 * 属性表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/{version}/admin/backend-attr")
@ApiVersion
@Api(tags = "属性控制器")
@Slf4j
public class ShsBackendAttributeController {

    @Autowired
    private ShsBackendAttributeService attributeService;

    @GetMapping()
    @ApiOperation("属性列表")
    public R list(@ApiParam("页码")@RequestParam("catId")Integer catId,
                  @ApiParam("类型")@RequestParam("type")String type){

        List<ShsBackendAttribute> list = attributeService.listByCatAndType(catId, type);
        return R.ok().data("data", list);
    }

    @PostMapping
    @ApiOperation("添加属性")
    public R add(@RequestBody ShsBackendAttribute  attribute){
        boolean save = attributeService.save(attribute);
        return save ? R.ok() : R.error();
    }

    @PutMapping
    @ApiOperation("编辑属性")
    public R edit(@RequestBody ShsBackendAttribute  attribute){
        boolean save = attributeService.saveOrUpdate(attribute);
        return save ? R.ok() : R.error();
    }


    @DeleteMapping("/{id}")
    @ApiOperation("编辑属性")
    public R delete(@ApiParam("id")@PathVariable("id")Integer id){
        boolean save = attributeService.removeById(id);
        return save ? R.ok() : R.error();
    }
}

