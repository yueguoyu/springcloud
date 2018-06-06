package com.ygy.dao;

import com.ygy.model.User;
import com.ygy.model.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class RedisDaoImpl implements RedisDao {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 用于存储关注用户id以及开始的时间
     */
    static ZSetOperations<String, Long> zSetOperations;
    /**
     * 用于存储用户信息
     */
    static HashOperations<String,String,Object> hashOperations;

    @Override
    public void add(UserFollow user) {
        zSetOperations=redisTemplate.opsForZSet();
        String key = "follow_" + user.getUserid();
        zSetOperations.add(key, user.getFollowUid(), user.getTime());
    }

    @Override
    public void delete(UserFollow user) {
        zSetOperations=redisTemplate.opsForZSet();
        String key = "follow_" + user.getUserid();
        zSetOperations.remove(key,user.getFollowUid());
    }

    @Override
    public void addUser(User user) {
        hashOperations=redisTemplate.opsForHash();
        String key="user_"+user.getUserid();
        HashMap<String,Object> hashMap=new HashMap<>();
        //userid为手机号码
         hashMap.put("userid",user.getUserid());
         hashMap.put("name",user.getName());
         hashMap.put("password",user.getPassword());
         hashMap.put("follows",user.getFollowes());
         hashMap.put("following",user.getFollowing());
         hashMap.put("posts",user.getPosts());
         hashMap.put("signup",user.getSignup());
        hashOperations.putAll(key,hashMap);
    }

    @Override
    public void deleteUser(long userid) {
        hashOperations=redisTemplate.opsForHash();
        String key="user_"+userid;
        hashOperations.delete(key);
    }
}