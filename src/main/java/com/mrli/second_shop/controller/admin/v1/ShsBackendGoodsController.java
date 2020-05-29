package com.mrli.second_shop.controller.admin.v1;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.dto.GoodsDTO;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.form.GoodsAddForm;
import com.mrli.second_shop.service.ShsBackendGoodsService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.UUID;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/{version}/admin/backend-goods")
@ApiVersion
@Api(tags = "商品控制器")
@Slf4j
public class ShsBackendGoodsController {

    @Autowired
    private ShsBackendGoodsService goodsService;

    @Autowired
    private GoodsDTO goodsDTO;

    @ApiOperation("商品列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        Page<ShsBackendGoods> pageQuery =  goodsService.pageQuery(page,limit, query, null);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @PostMapping
    @ApiOperation("添加商品")
    public R add(@RequestBody GoodsAddForm goods, Principal principal){
        log.info(JSONObject.toJSONString(goods));
        String username = principal.getName();
        boolean save = goodsService.saveGoods(goods, username);
        return save ? R.ok() : R.error();
    }

    @PutMapping
    @ApiOperation("更新商品")
    public R edit(@RequestBody ShsBackendGoods  goods){
        boolean save = goodsService.saveOrUpdate(goods);
        return save ? R.ok() : R.error();
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除商品")
    public R delete(@ApiParam("id")@PathVariable("id")Integer id){
        boolean save = goodsService.removeById(id);
        return save ? R.ok() : R.error();
    }

    @ApiOperation("检查商品名称是否存在")
    @GetMapping("/checkName")
    public R checkName(@ApiParam(name="query",value = "查詢參數") @RequestParam(value="name")String name){
        ShsBackendGoods goods  =  goodsService.getByName(name);
        Boolean has = (goods!=null ? true : false);
        return R.ok().data("data",has);
    }

    @ApiOperation("上传文件")
    @RequestMapping("/upload")
    public R upload(MultipartFile file){
        String path = "";
        String fileName = "";
        try {
            log.info(ShsBackendGoodsController.class.getResource("/").getPath());
            String parentPath = ShsBackendGoodsController.class.getResource("/static/tmp/").getPath();
            fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            path = parentPath + fileName;
            log.info(path);
            File tmpFile = new File(path);
            file.transferTo(tmpFile);
        }catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
        return R.ok().data("path", "/static/tmp/" + fileName).data("url","http://localhost/tmp/" + fileName);
    }
}

