package com.ygy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

/**
 * @author ygy
 * @date 2018/5/26
 * 点赞功能
 */
@Service
public class HitDaoImpl implements HitDao {
    /**
     * 用于存储点过赞的用户
     */
    static SetOperations<Long, String> setoperations;
    /**
     * 用于存储每条动态的赞数
     */
    static HashOperations<String, Long, Integer> operations;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞功能每个用户只能点一次
     *
     * @param tid
     * @param username
     */
    @Override
    public void addhit(Long tid, String username) {
        setoperations = redisTemplate.opsForSet();
        operations = redisTemplate.opsForHash();

        int hit;
        try {
            hit = operations.get("Timelinehit", tid);
        } catch (Exception e) {
            hit = 0;
        }
        if (!setoperations.isMember(tid, username)) {
            hit++;
            setoperations.add(tid, username);
            operations.put("Timelinehit", tid, hit);
        } else {
            System.out.println("hahttt");
        }
    }

    /**
     * 通过动态id获取点赞数
     *
     * @param tid
     * @return
     */
    @Override
    public int selecthit(Long tid) {
        boolean re = redisTemplate.hasKey("Timelinehit");
        int hit = 0;
        operations = redisTemplate.opsForHash();
        hit = operations.get("Timelinehit", tid);
        return hit;
    }
}