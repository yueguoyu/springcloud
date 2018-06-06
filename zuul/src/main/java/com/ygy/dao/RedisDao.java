package com.ygy.dao;

import com.ygy.model.User;
import com.ygy.model.UserFollow;

/**
 * @author ygy
 * @date 2018/5/30
 * 将用户信息放在redis数据中
 */
public interface RedisDao {
    /**
     * 增加关注
     *
     * @param
     * @param
     */
    void add(UserFollow user);

    /**
     * 删除关注
     *
     * @param
     */
    void delete(UserFollow user);

    /**
     * 注册用户
     * @param user
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param userid
     */
    void deleteUser(long userid);
}