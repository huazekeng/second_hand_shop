package com.mrli.second_shop.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.constant.ResultCodeEnum;
import com.mrli.second_shop.entity.ShsBackendGoods;
import com.mrli.second_shop.entity.ShsFrontRequire;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.form.SearchForm;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontRequireService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/api/require")
@Api(tags = "聊天控制器")
@Slf4j
public class ShsFrontRequireController {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShsFrontRequireService requireService;

    @PostMapping
    public R add(HttpServletRequest request, @RequestBody ShsFrontRequire require) {
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        QueryWrapper<ShsFrontRequire> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("status", 1);
        List<ShsFrontRequire> list = requireService.list(queryWrapper);
        if(list.size()>=5) {
            return R.error().setResult(ResultCodeEnum.REQUIRE_EXCEED);
        }
        require.setUsername(user.getUsername());
        this.requireService.save(require);
        return R.ok();
    }

    @ApiOperation("获取自生求购列表")
    @GetMapping("/{page}/{limit}")
    public R list(HttpServletRequest request, @ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        Page<ShsFrontRequire> pageQuery =  this.requireService.pageQuery(page,limit,query, user.getUsername());
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @ApiOperation("更新求购")
    @PutMapping()
    public R list(HttpServletRequest request, @RequestBody ShsFrontRequire require){
        if(require.getStatus() == 1) {
            String authHeader = request.getHeader(jwtConfig.getHeader());
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
            ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
            QueryWrapper<ShsFrontRequire> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            queryWrapper.eq("status", 1);
            List<ShsFrontRequire> list = requireService.list(queryWrapper);
            if (list.size() >= 5) {
                return R.error().setResult(ResultCodeEnum.REQUIRE_EXCEED);
            }
        }
        this.requireService.saveOrUpdate(require);
        return R.ok();
    }

    @ApiOperation("更新求购")
    @GetMapping("meunList")
    public R meunList() {
        QueryWrapper<ShsFrontRequire> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("status", 1);
        Page<ShsFrontRequire> page = new Page<>();
        page.setSize(12);
        page.setCurrent(1);
        List<ShsFrontRequire> records = requireService.page(page, queryWrapper).getRecords();
        return R.ok().data("list", records);
    }

    @ApiOperation("查询")
    @RequestMapping("/search")
    public R search(@RequestBody SearchForm search){
        Page<ShsFrontRequire> page = requireService.pageQuery(search);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords()).data("hasNextPage", page.hasNext());
    }
}

