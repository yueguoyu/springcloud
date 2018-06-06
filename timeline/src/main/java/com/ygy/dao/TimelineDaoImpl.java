package com.ygy.dao;

import com.ygy.mapper.TimelineMapper;
import com.ygy.mapper.UsertimelineMapper;
import com.ygy.model.Timeline;
import com.ygy.model.Usertimeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.beans.Transient;
import java.util.*;
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
    @Autowired
    UsertimelineMapper usertimelineMapper;

    @Override
    @Transient
    public void add(Timeline timeline) {
        redisDao.addTimeline(timeline.getId(), timeline);
        redisDao.addUserTimeline(timeline.getUserid(), timeline);
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
        Usertimeline usertimeline = new Usertimeline();
        for (Map map : mapList) {
            timeline = new Timeline();
            timeline.setUserid((Long) map.get("uid"));
            timeline.setUsername((String) map.get("username"));
            timeline.setText((String) map.get("message"));
            timeline.setFile((String) map.get("file"));
            timeline.setTime((Date) map.get("posted"));
            timeline.setThumbsup((Integer) map.get("thumbsup"));

            //使用给的tid
            timeline.setId((Long) map.get("tid"));
            usertimeline.setUserid("time_" + map.get("uid"));
            usertimeline.setTime((Date) map.get("posted"));
            usertimeline.setTid((Long) map.get("tid"));
            try {
                mapper.insert(timeline);
            } catch (Exception e) {
                break;
            }
            try {
                usertimelineMapper.insert(usertimeline);
            } catch (Exception e) {
                break;
            }

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
        Timeline timeline = null;
        try {
            timeline = redisDao.getTimelineByID(tid);
        } catch (Exception e) {
            timeline = mapper.selectByPrimaryKey(tid);
            redisDao.addTimeline(tid, timeline);
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
        List<Timeline> list = new ArrayList<>();
        Set<Long> set = null;
        try {
            set = redisDao.getSetTid(userid);
            for (long tid : set) {
                Timeline timeline = redisDao.getTimelineByID(tid);
                list.add(timeline);
            }
        } catch (Exception e) {
            list = mapper.selectByUserID(userid);
            for (Timeline timeline : list) {
                redisDao.addTimeline(timeline.getId(), timeline);
                redisDao.addUserTimeline(timeline.getUserid(), timeline);
            }
        }
        return list;
    }
}