package com.mrli.second_shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrli.second_shop.entity.ShsBackendReport1;
import com.mrli.second_shop.mapper.ShsBackendReport1Mapper;
import com.mrli.second_shop.service.ShsBackendReport1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrli.second_shop.vo.admin.ReportVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HZK
 * @since 2020-02-18
 */
@Service
public class ShsBackendReport1ServiceImpl extends ServiceImpl<ShsBackendReport1Mapper, ShsBackendReport1> implements ShsBackendReport1Service {

    @Override
    public ReportVo reportData() {
        ReportVo vo = new ReportVo();
        QueryWrapper<ShsBackendReport1> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("rp1_date");
        queryWrapper.orderByAsc("rp1_area");
        List<ShsBackendReport1> list = this.list(queryWrapper);
        // Set<String> legendData = new HashSet<>();
        Set<String> xAixsData = new HashSet<>();
        Map<String,Object> xAixs = new HashMap<>();
        xAixs.put("data",xAixsData);
        Map<String, ReportVo.Serie> series = new HashMap<>();
        for(ShsBackendReport1 report: list){
            xAixsData.add(DateFormatUtils.format(report.getRp1Date(),"yyyy-MM-dd"));
            if(!series.containsKey(report.getRp1Area())){
                ReportVo.Serie serie = new ReportVo.Serie();
                serie.setName(report.getRp1Area());
                serie.setStack("总量");
                serie.setType("line");
                serie.setAreaStyle(new HashMap<>());
                series.put(report.getRp1Area(),serie);
            }
            series.get(report.getRp1Area()).getData().add(report.getRp1UserCount());
        }
        vo.getLegend().put("data",series.keySet());
        vo.getXAxis().add(xAixs);
        vo.setSeries(series.values());
        return vo;
    }
}
