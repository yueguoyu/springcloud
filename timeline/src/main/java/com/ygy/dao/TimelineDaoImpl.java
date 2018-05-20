package com.ygy.dao;

import com.ygy.mapper.TimelineMapper;
import com.ygy.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.beans.Transient;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ygy
 * @date 2018/5/10
 * 增加时间轴
 */
@Service
public class TimelineDaoImpl implements TimelineDao {
    @Autowired
    TimelineMapper mapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisDaoImpl redisDao;

    @Override
    @Transient
    public void add(Timeline timeline) {
        redisDao.addTimeline(timeline.getId(), timeline);
        redisDao.addUserTimeline(timeline.getUserid(),timeline);
    }

    /**
     * 每天0点触发
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")// 10秒执行一次
    public void addToMysql() {
        List<Map<String, Object>> mapList = redisDao.getTimelines();
        Timeline timeline;
        for (Map map : mapList) {
            timeline = new Timeline();
            timeline.setUserid((Long) map.get("uid"));
            timeline.setUsername((String) map.get("username"));
            timeline.setText((String) map.get("message"));
            timeline.setFile((String) map.get("file"));
            timeline.setTime((Date) map.get("posted"));
            timeline.setThumbsup((Integer) map.get("thumbsup"));
            mapper.insert(timeline);
        }
    }

    @Transient
    @Override
    public int delete(long tid) {

        return mapper.deleteByPrimaryKey(tid);
    }

    @Transient
    @Override
    public Timeline selectById(long tid) {
        Timeline  timeline=null;
        try {
            timeline = redisDao.getTimelineByID(tid);
        }catch (Exception e){
            timeline=mapper.selectByPrimaryKey(tid);
            redisDao.addTimeline(tid,timeline);
        }
        return timeline;
    }

    //获取当前时间前8小时的所有动态
    @Override
    public List<Timeline> selectByTime(Date date) {
//      return redisDao.getTimelinesByTime(123);
        return mapper.selectByTime();
    }

    @Override
    public List<Timeline> selectByUserID(long userid) {
        return mapper.selectByUserID(userid);
    }
}