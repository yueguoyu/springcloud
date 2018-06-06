package com.ygy.controller;

import com.ygy.model.User;
import com.ygy.model.UserFollow;
import com.ygy.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class LoginController {
    @Autowired
    private RedisDao redisDao;
    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/loginDao",method = RequestMethod.POST)
    public String register(@RequestParam User user){
        redisDao.addUser(user);
        return "";
    }

    /**
     * 删除用户
     * @param userid
     * @return
     */

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String deleteUser(@RequestParam long userid){
        redisDao.deleteUser(userid);
        return "";
    }

    /**
     * 加关注
     * @param userFollow
     * @return
     */
    @GetMapping("/addfollow")
    public String addFollow(UserFollow userFollow){
        redisDao.add(userFollow);
        return "";
    }

    /**
     * 删除关注
     * @param userFollow
     */
    @GetMapping("/deletefollow")
    public void  deleteFollow(UserFollow userFollow){
        redisDao.delete(userFollow);
    }

}