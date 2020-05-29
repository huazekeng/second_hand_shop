package com.mrli.second_shop.controller.admin.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrli.second_shop.entity.ShsFrontOrder;
import com.mrli.second_shop.service.ShsFrontOrderService;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/{version}/admin/backend-order")
@ApiVersion
@Api(tags = "订单管理")
@Slf4j
public class ShsFrontOrderController {

    @Autowired
    private ShsFrontOrderService orderService;

    @ApiOperation(value = "订单列表")
    @GetMapping("/{page}/{limit}")
    public R list(@ApiParam(name="page",value = "当前页数")@PathVariable(value = "page") int page, @PathVariable(value = "limit") @ApiParam(name="limit",value = "每页条目数")int limit,
                  @ApiParam(name="query",value = "查詢參數") @RequestParam(value="query",required = false)String query){
        Page<ShsFrontOrder> pageQuery =  orderService.pageQuery(page,limit,query);
        return R.ok().data("total",pageQuery.getTotal()).data("rows",pageQuery.getRecords());
    }
}

