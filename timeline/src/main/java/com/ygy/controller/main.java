package com.ygy.controller;

import com.ygy.dao.GetCommentAndHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class main {
    @RequestMapping("/")
    public String index() {
        return "main";
    }

    @Autowired
    GetCommentAndHit getCommentAndHit;

    @GetMapping("/gethit")
    public String gethit(@RequestParam long tid) {
        return "" + getCommentAndHit.getHit(tid);
    }

    @GetMapping("/getcomment")
    public Map getcomment(@RequestParam long tid) {
        return getCommentAndHit.getComment(tid).get(0);
    }
}