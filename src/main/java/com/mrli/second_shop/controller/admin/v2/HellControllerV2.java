package com.mrli.second_shop.controller.admin.v2;

import com.mrli.second_shop.version.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{version}/hello")
@ApiVersion(2)
public class HellControllerV2 {

    @GetMapping()
    @ResponseBody
    public String hello(){
        return "Hello1";
    }

}
