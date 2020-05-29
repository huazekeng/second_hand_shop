package com.mrli.second_shop.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontForum;
import com.mrli.second_shop.entity.ShsFrontReplay;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontForumService;
import com.mrli.second_shop.service.ShsFrontReplayService;
import com.mrli.second_shop.service.ShsFrontRequireService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-03-15
 */
@RestController
@RequestMapping("/api/replay")
@Api(tags = "论坛控制器")
@Slf4j
public class ShsFrontReplayController {
    @Autowired
    private ShsFrontForumService forumService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShsFrontReplayService replayService;


    @ApiOperation("回复列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="forumId",value = "帖子") @RequestParam(value="forumId",required = false)Integer forumId){

        Page<ShsFrontReplay> pageQuery = new Page<ShsFrontReplay>();
        pageQuery.setSize(limit);
        pageQuery.setCurrent(page);
        QueryWrapper<ShsFrontReplay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("forum_id", forumId);
        // queryWrapper.orderByDesc("gmt_create");
        this.replayService.page(pageQuery, queryWrapper);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }

    @ApiOperation("回帖")
    @PostMapping()
    @Transactional
    public R post(@RequestBody ShsFrontReplay replay){
        replay.setAgreementList("[]");
        this.replayService.save(replay);
        ShsFrontForum byId = forumService.getById(replay.getForumId());
        byId.setReplayCount(byId.getReplayCount() + 1);
        this.forumService.saveOrUpdate(byId);
        return R.ok();
    }

}

