package com.mrli.second_shop.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.entity.ShsFrontChat;
import com.mrli.second_shop.entity.ShsFrontUser;
import com.mrli.second_shop.security.JwtConfig;
import com.mrli.second_shop.service.ShsFrontChatService;
import com.mrli.second_shop.util.RedisUtils;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-03-08
 */
@RestController
@RequestMapping("/api/chat")
@Api(tags = "聊天控制器")
@Slf4j
public class ShsFrontChatController {

    @Autowired
    private ShsFrontChatService chatService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("聊天列表")
    @GetMapping("/{page}/{limit}")
    public R list(HttpServletRequest request, @ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="username",value = "查詢參數") @RequestParam(value="username",required = false)String username,
                  @ApiParam(name="receiver",value = "查詢參數") @RequestParam(value="receiver",required = false)String receiver){
        if(page == -1 || limit == -1) {
            if(StringUtils.isBlank(receiver)) {
                List<ShsFrontChat> list = chatService.lastChats(username);
                return R.ok().data("list", list);
            } else {
                QueryWrapper<ShsFrontChat> queryWrapper = new QueryWrapper<>();
                queryWrapper.and(wrapper -> wrapper.eq("send_id", username).eq("talk_id", receiver))
                        .or().eq("talk_id", username).eq("send_id", receiver);
                queryWrapper.orderByAsc("gmt_create");
                List<ShsFrontChat> list = chatService.list(queryWrapper);
                return R.ok().data("list", list);
            }
        }

        return null;

    }

}

