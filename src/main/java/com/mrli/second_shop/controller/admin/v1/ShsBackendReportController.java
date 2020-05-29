package com.mrli.second_shop.controller.admin.v1;


import com.mrli.second_shop.service.ShsBackendReport1Service;
import com.mrli.second_shop.service.ShsBackendReport2Service;
import com.mrli.second_shop.service.ShsBackendReport3Service;
import com.mrli.second_shop.version.ApiVersion;
import com.mrli.second_shop.vo.R;
import com.mrli.second_shop.vo.admin.ReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/{version}/admin/backend-report")
@ApiVersion
@Api(tags = "数据统计")
@Slf4j
public class ShsBackendReportController {

    @Autowired
    private ShsBackendReport1Service report1Service;

    @Autowired
    private ShsBackendReport2Service report2Service;

    @Autowired
    private ShsBackendReport3Service report3Service;


    @GetMapping("/{type}")
    @ApiOperation("数据报表")
    public R list(@ApiParam("报表类型")@PathVariable("type")Integer type){
        ReportVo vo = null;
        if(type == 1) {
            vo = report1Service.reportData();
        }
        return R.ok().data("data", vo);
    }
}

