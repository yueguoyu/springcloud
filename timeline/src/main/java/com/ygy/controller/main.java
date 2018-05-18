package com.ygy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class main {
    @RequestMapping("/")
    public String index(){
        return "main";
    }
}