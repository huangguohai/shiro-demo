package com.imooc.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // 权限名指定为 test:update
    @RequiresPermissions({"test:update"})
    @GetMapping("/update")
    public String test1() {
        return "有update权限";
    }

    // 权限名指定为：test:add
    @RequiresPermissions({"test:add"})
    @GetMapping("/add")
    public String test2(){
        return "有add权限";
    }

}
