package com.mrli.second_shop.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.controller.admin.v1.ShsBackendGoodsController;
import com.mrli.second_shop.dto.GoodsDTO;
import com.mrli.second_shop.entity.ShsBackendAttribute;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.form.GoodsAddForm;
import com.mrli.second_shop.form.SearchForm;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsBackendAttributeService;
import com.mrli.second_shop.service.ShsBackendCategoryService;
import com.mrli.second_shop.service.ShsBackendGoodsService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.R;
import com.mrli.second_shop.vo.admin.CategoryTreeVo;
import com.mrli.second_shop.vo.api.FrontGoods;
import com.mrli.second_shop.vo.api.MenuItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goods")
@Api(tags = "商品控制器")
@Slf4j
public class GoodController {

    @Autowired
    ShsBackendGoodsService goodsService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private ShsBackendCategoryService categoryService;


    @Autowired
    private ShsBackendAttributeService attributeService;

    @Autowired
    private GoodsDTO goodsDTO;


    @ApiOperation("获取广告菜单商品")
    @GetMapping("/{page}/{limit}")
    public R list(HttpServletRequest request, @ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        Page<ShsBackendGoods> pageQuery =  goodsService.pageQuery(page,limit,query, user.getUsername());
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @GetMapping("/{page}/{limit}/{level}")
    @ApiOperation("分类列表")
    public R list(@ApiParam("页码")@PathVariable("page")Integer page,
                  @ApiParam("每页条目数")@PathVariable("limit")Integer limit,
                  @ApiParam("查询参数")@PathVariable("level")Integer level){

        Page<CategoryTreeVo> categoryTreeVoPage = categoryService.pageLevelQuery(page, limit, level);

        return R.ok().data("total",categoryTreeVoPage.getTotal()).data("rows",categoryTreeVoPage.getRecords());
    }

    @GetMapping("/attr")
    @ApiOperation("属性列表")
    public R list(@ApiParam("页码")@RequestParam("catId")Integer catId,
                  @ApiParam("类型")@RequestParam("type")String type){

        List<ShsBackendAttribute> list = attributeService.listByCatAndType(catId, type);
        return R.ok().data("data", list);
    }


    @ApiOperation("上传文件")
    @RequestMapping("/upload")
    public R upload(MultipartFile file,Integer type){
        String path = "";
        String fileName = "";
        String temp = "tmp";
        try {
            if(type!=null && type == 5) {
                temp = "images/require";
            }
            log.info(ShsBackendGoodsController.class.getResource("/").getPath());
            String parentPath = ShsBackendGoodsController.class.getResource("/static/" + temp + "/").getPath();
            fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            path = parentPath + fileName;
            log.info(path);
            File tmpFile = new File(path);
            file.transferTo(tmpFile);
        }catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
        return R.ok().data("path", "/static/" + temp + "/" + fileName).data("url","http://localhost/" + temp + "/" + fileName).data("type",type);
    }


    @PostMapping
    @ApiOperation("添加商品")
    public R add(HttpServletRequest request, @RequestBody GoodsAddForm goods, Principal principal){
        // String username = principal.getName();
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        String username = user.getUsername();
        log.info(JSONObject.toJSONString(goods));
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
    @RequestMapping("/uploadPic")
    public R uploadPic(MultipartFile file,Integer type, Integer id){
        String path = "";
        String fileName = "";
        try {
            log.info(ShsBackendGoodsController.class.getResource("/").getPath());
            String parentPath = ShsBackendGoodsController.class.getResource("/static/images/goods").getPath();
            fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            path = parentPath + fileName;
            log.info(path);
            File tmpFile = new File(path);
            file.transferTo(tmpFile);
            String url = "http://localhost/images/goods/" + fileName;
            ShsBackendGoods goods = goodsService.getById(id);
            if (type == 1)
                goods.setBannerPic(url);
            else if (type == 2) goods.setSlidePic(url);
            else if (type == 3 ) goods.setAdsPic(url);
            goodsService.saveOrUpdate(goods);
        }catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
        return R.ok().data("path", "/static/images/goods/" + fileName).data("url","http://localhost/images/goods/" + fileName).data("type",type);
    }

    @ApiOperation("根据分类获取商品")
    @RequestMapping("/cate/{catId}")
    public R goodsByCate(@PathVariable("catId") Integer catId){
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cat_id", catId);
        queryWrapper.eq("status", 1);
        queryWrapper.orderByDesc("gmt_create");
        List<ShsBackendGoods> list = goodsService.list(queryWrapper);
        return R.ok().data("list",goodsDTO.toDto(list));
    }

    @ApiOperation("获取轮播图")
    @RequestMapping("/slide")
    public R slideGoods(){
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_slide", 1);
        queryWrapper.eq("status", 1);
        queryWrapper.orderByDesc("gmt_create");
        List<ShsBackendGoods> list = goodsService.list(queryWrapper);
        return R.ok().data("list",list);
    }

    @ApiOperation("获取menu")
    @RequestMapping("/menu_list")
    public R munuList(){
        List<MenuItem> list = goodsService.getMenuList();
        return R.ok().data("list",list);
    }

    @ApiOperation("获取广告图")
    @RequestMapping("/ads")
    public R adsList(){
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("is_ads", 1);
        queryWrapper.eq("status", 1);
        Page<ShsBackendGoods> page = new Page<>();
        page.setSize(4);
        page.setCurrent(1);
        return R.ok().data("list",goodsService.page(page, queryWrapper).getRecords());
    }

    @ApiOperation("获取Banner图")
    @RequestMapping("/banner")
    public R banner(){
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("is_banner", 1);
        queryWrapper.eq("status", 1);
        Page<ShsBackendGoods> page = new Page<>();
        page.setSize(1);
        page.setCurrent(1);
        List<ShsBackendGoods> records = goodsService.page(page, queryWrapper).getRecords();
        return R.ok().data("list",records == null ? null : records.get(0));
    }

    @ApiOperation("获取Banner图")
    @RequestMapping("/promote")
    public R promoteList(){
        QueryWrapper<ShsBackendGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("is_promote", 1);
        queryWrapper.eq("status", 1);
        Page<ShsBackendGoods> page = new Page<>();
        page.setSize(12);
        page.setCurrent(1);
        List<ShsBackendGoods> records = goodsService.page(page, queryWrapper).getRecords();
        return R.ok().data("list", goodsDTO.toDto(records));
    }

    @ApiOperation("获取商品信息")
    @RequestMapping("/{id}")
    public R getInfo(@PathVariable("id")Integer id){
        ShsBackendGoods goods = goodsService.getById(id);
        return R.ok().data("data", goodsDTO.toCompileDto(goods));
    }

    @ApiOperation("查询")
    @RequestMapping("/search")
    public R search(@RequestBody SearchForm search){
        Page<ShsBackendGoods> page = goodsService.pageQuery(search);
        return R.ok().data("total",page.getTotal()).data("list",goodsDTO.toDto(page.getRecords())).data("hasNextPage", page.hasNext());
    }

}
