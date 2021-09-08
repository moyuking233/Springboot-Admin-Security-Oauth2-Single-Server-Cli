package com.nga.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//用来测试登录成功后能否被访问
@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
