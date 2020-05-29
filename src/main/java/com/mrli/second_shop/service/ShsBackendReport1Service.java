package com.mrli.second_shop.service;

import com.mrli.second_shop.entity.ShsBackendReport1;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrli.second_shop.vo.admin.ReportVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
public interface ShsBackendReport1Service extends IService<ShsBackendReport1> {

    /**
     * 获取报表数据
     * @return
     */
    ReportVo reportData();
}
