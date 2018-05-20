package com.ygy.dao;


import com.ygy.mapper.TimelineMapper;
import com.ygy.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisDaoImpl implements RedisDao {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TimelineMapper mapper;

    static HashOperations<Long, String, Object> useroperations;
    static ZSetOperations<String, Long> zSetOperations;;
    static ArrayList<Long> arrayList = new ArrayList<>();

    @Override
    public void addTimeline(long tid, Timeline timeline) {
        useroperations = redisTemplate.opsForHash();
        System.out.println("::::::" + tid + "   " + timeline.getText());
        useroperations.put(tid, "message", timeline.getText());
        useroperations.put(tid, "file", timeline.getFile());
        useroperations.put(tid, "posted", timeline.getTime());
        useroperations.put(tid, "uid", timeline.getUserid());
        useroperations.put(tid, "username", timeline.getUsername());
        useroperations.put(tid, "thumbsup", timeline.getThumbsup());
        arrayList.add(tid);
    }

    @Override
    public List<Map<String, Object>> getTimelines() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (arrayList != null) {
            for (Long tid : arrayList) {
                Map<String, Object> map = useroperations.entries(tid);
                mapList.add(map);
            }
        } else {
            return null;
        }
        return mapList;
    }

    @Override
    public Timeline getTimelineByID(Long id) {
        Map<String, Object> map = useroperations.entries(id);
        Timeline timeline = new Timeline();
        timeline.setUserid((Long) map.get("uid"));
        timeline.setUsername((String) map.get("username"));
        timeline.setText((String) map.get("message"));
        timeline.setFile((String) map.get("file"));
        timeline.setTime((Date) map.get("posted"));
        timeline.setThumbsup((Integer) map.get("thumbsup"));
        timeline.setId(id);
        return timeline;
    }

    @Override
    public void addUserTimeline(long uid, Timeline timeline) {
        String key = "timelineUid" + uid;
        zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add(key,(long)12,12);
        zSetOperations.add(key, timeline.getId(), timeline.getTime().getTime());
    }

    @Override
    public List<Timeline> getTimelinesByTime(long uid) {
        zSetOperations = redisTemplate.opsForZSet();
        String key = "time_" + uid;
        Date date=new Date(System.currentTimeMillis());
        Set<Long> set = zSetOperations.reverseRange(key, date.getTime()-1000000000, date.getTime());
        List<Timeline> list = new ArrayList<>();
        for (long tid : set) {
            Timeline timeline = null;
            try {
                timeline = getTimelineByID(tid);
                System.out.println(tid);
            } catch (Exception e) {
                timeline = mapper.selectByPrimaryKey(tid);
                this.addTimeline(tid, timeline);

            } finally {
                list.add(timeline);
                System.out.println(tid);
            }
        }
        return list;
    }
    //再改改
    @Override
    public void getSetTid(long uid) {
        String key = "time_" + 123;
        Set<Long> set=new HashSet<>();
            set = zSetOperations.range(key, 0, System.currentTimeMillis());
        long re=0;
        for (long a:set){
            System.out.println("    "+a);
        }
    }
}