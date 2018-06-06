package com.ygy.controller;

import com.ygy.dao.CommentDao;
import com.ygy.dao.HitDao;
import com.ygy.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ygy
 * 测试mysql
 */
@RestController
public class test {
    @Autowired
    private HitDao hitDao;
    @Autowired
    private CommentDao dao;
    @Autowired
    Comment comment;
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String test(){
        comment.setCid(2);
        comment.setUserid((long)2);
        comment.setText("测试！！！！");
        comment.setPuserid((long)1);
        comment.setTid((long)1);
        dao.addComment(comment,comment.getTid());
        return "success";
    }
    @GetMapping("/getcomment")
    public String getComment(@RequestParam long tid){
     List list=dao.getComment(tid);
       return list.get(0).toString();
    }
    @GetMapping("/addhit")
    public String addHit(){
        hitDao.addhit((long)1,"ygy1");
        return "success";
    }

    @RequestMapping("/gethit")
    public String getHit(@RequestParam long tid){

        return ""+hitDao.selecthit(tid);
    }

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from ";
    }
}