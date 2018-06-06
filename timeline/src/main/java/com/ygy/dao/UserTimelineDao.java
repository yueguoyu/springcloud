package com.ygy.dao;

import com.ygy.model.Usertimeline;

import java.util.List;

/**
 * @author ygy
 * @date 2018/5/21
 */
public interface UserTimelineDao {
    /**
     * 增加用户动态线
     *
     * @param usertimeline
     */
    void addUserTimeline(Usertimeline usertimeline);

    /**
     * 查询用户动态的id
     *
     * @param userid
     * @return tid
     */
    List<Long> selectUserTimeline(String userid);
}
