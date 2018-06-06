package com.ygy.controller;


import com.ygy.alyundao.AlyunDao;
import com.ygy.dao.RedisDao;
import com.ygy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ygy
 * @date 2018/6/2
 * 短信注册
 */
@Controller
public class MessageLogin {
    @Autowired
    private AlyunDao alyunDao;
    @Autowired
    private RedisDao redisDao;
    private int code;

    /**
     * 获取短信验证码
     */
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    @ResponseBody
    public void messageLogin(){
        try {
            code= alyunDao.addMessage("13572811105");
            System.out.println(code);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 短信验证注册
     * @param newcode
     * @param user
     */
    @RequestMapping(value = "/logon",method = RequestMethod.POST)
    public String  login(@RequestParam int newcode,@RequestParam User user){
        if (newcode==code){
            redisDao.addUser(user);
            //返回登录页面
            return "";
        }
        //返回注册页面
        return "";
    }


}