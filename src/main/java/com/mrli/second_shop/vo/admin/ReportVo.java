package com.mrli.second_shop.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;

@ApiModel("数据报表对象")
@Data
public class ReportVo {

    @ApiModel("Serie对象")
    @Data
    public static class Serie{
        @ApiModelProperty("name")
        private String name;
        @ApiModelProperty("type")
        private String type;
        @ApiModelProperty("stack")
        private String stack;
        @ApiModelProperty("areaStyle")
        private Map<String,Object> areaStyle = new HashMap<>();
        @ApiModelProperty("data")
        private List<Object> data = new ArrayList<>();
    }
    @ApiModelProperty("legend")
    private Map<String,Object> legend = new HashMap<>();

    @ApiModelProperty("xAxis")
    private List<Map<String,Object>> xAxis = new ArrayList<>();

    @ApiModelProperty("series")
    private Collection<Serie> series = new ArrayList<>();


}
