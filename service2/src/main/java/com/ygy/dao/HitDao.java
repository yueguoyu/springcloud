package com.ygy.dao;

/**
 * @author ygy
 * @date 2018/5/26
 */
public interface HitDao {
    /**
     * 点赞
     * @param tid
     * @param username
     */
    void addhit(Long tid,String username);

    /**
     * 获取赞数
     * @param tid
     * @return
     */
    int selecthit(Long tid);
}