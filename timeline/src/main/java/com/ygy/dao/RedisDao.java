package com.ygy.dao;

import com.ygy.model.Timeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ygy
 * @date 2018/5/21
 */
public interface RedisDao {
    /**
     * 增加用户动态到HashOperations
     *
     * @param tid
     * @param timeline
     */
    void addTimeline(long tid, Timeline timeline);

    /**
     * @return
     */
    List<Map<String, Object>> getTimelines();

    /**
     * 通过tid获取用户动态
     *
     * @param tid
     * @return
     */
    Timeline getTimelineByID(Long tid);

    /**
     * 以用户uid为key将tid和时间戳存到 redis的zst中
     *
     * @param uid
     * @param timeline
     */
    void addUserTimeline(long uid, Timeline timeline);

    /**
     * 返回用户时间线中tid的集合
     *
     * @param uid
     * @return
     */
    Set<Long> getSetTid(long uid);
}