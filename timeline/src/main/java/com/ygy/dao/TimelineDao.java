package com.ygy.dao;

import com.ygy.model.Timeline;


import java.util.Date;
import java.util.List;

/**
 * @author ygy
 * @date 2018/5/17
 */
public interface TimelineDao {
    /**
     * 增加动态
     * @param timeline
     */
    void add(Timeline timeline);

    /**
     * 删除动态
     * @param tid
     * @return
     */
    int delete(long tid);

    /**
     * 通过id查询动态
     * @param tid
     * @return
     */
    Timeline selectById(long tid);

    /**
     * 查询8小时以内的所有动态
     * @return
     */
    List<Timeline> selectByTime(Date date);

    /**
     *
     * @return
     */
    List<Timeline> selectByUserID(long userid);
    void addToMysql();
}