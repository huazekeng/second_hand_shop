package com.mrli.second_shop.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontForum;
import com.mrli.second_shop.entity.ShsFrontReplay;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontForumService;
import com.mrli.second_shop.service.ShsFrontReplayService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/api/forum")
@Api(tags = "论坛控制器")
@Slf4j
public class ShsFrontForumController {

    @Autowired
    private ShsFrontForumService forumService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShsFrontReplayService replayService;

    @ApiOperation("帖子列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        Page<ShsFrontForum> pageQuery =  forumService.pageQuery(page,limit,query, null);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @ApiOperation("发帖")
    @PostMapping()
    public R post(HttpServletRequest request , @RequestBody ShsFrontForum forum){
        String authHeader = request.getHeader(jwtConfig.getHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length() + 1);
        ShsFrontUser user = (ShsFrontUser) redisUtils.get(authToken);
        forum.setUsername(user.getUsername());
        this.forumService.save(forum);
        ShsFrontReplay replay = new ShsFrontReplay();
        replay.setUsername(user.getUsername());
        replay.setContent(forum.getContent());
        replay.setAgreementCount(0);
        replay.setAgreementList("[]");
        replay.setFloor(1);
        replay.setForumId(forum.getId());
        replayService.save(replay);
        return R.ok();
    }

    @ApiOperation("删除帖子")
    @DeleteMapping("/{id}")
    public R put(@PathVariable("id") Integer id){
        this.forumService.removeById(id);
        return R.ok();
    }

    @ApiOperation("我的帖子")
    @GetMapping("/my/{id}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query,
                  @ApiParam(name="username",value = "用户名") @RequestParam(value="username",required = false)String username){
        Page<ShsFrontForum> pageQuery =  forumService.pageQuery(page,limit,query, username);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @ApiOperation("我的帖子")
    @GetMapping("{id}")
    public R detail(@PathVariable("id") Integer id){
        return R.ok().data("data", this.forumService.getById(id));
    }


}

