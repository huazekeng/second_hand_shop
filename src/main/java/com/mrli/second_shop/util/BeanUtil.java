package com.mrli.second_shop.util;


import com.alibaba.fastjson.JSONObject;

public class BeanUtil {

    public static String beanToJsonString(Object obj){
        return JSONObject.toJSONString(obj);
    }
}
