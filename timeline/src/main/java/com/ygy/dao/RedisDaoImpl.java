package com.ygy.dao;


import com.ygy.mapper.TimelineMapper;
import com.ygy.mapper.UsertimelineMapper;
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
    private RedisTemplate redisTemplate;
    @Autowired
    private TimelineMapper mapper;
    @Autowired
    private UsertimelineMapper usertimelineMapper;

    static HashOperations<Long, String, Object> useroperations;
    static ZSetOperations<String, Long> zSetOperations;
    ;
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
        useroperations.put(tid, "tid", tid);
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
    public Timeline getTimelineByID(Long tid) {
        Map<String, Object> map = useroperations.entries(tid);
        Timeline timeline = new Timeline();
        timeline.setUserid((Long) map.get("uid"));
        timeline.setUsername((String) map.get("username"));
        timeline.setText((String) map.get("message"));
        timeline.setFile((String) map.get("file"));
        timeline.setTime((Date) map.get("posted"));
        timeline.setThumbsup((Integer) map.get("thumbsup"));
        timeline.setId(tid);
        return timeline;
    }

    @Override
    public void addUserTimeline(long uid, Timeline timeline) {
        String key = "time_" + uid;
        zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, timeline.getId(), timeline.getTime().getTime());
    }

    //获取指定用户的时间线
    @Override
    public Set<Long> getSetTid(long uid) {
        String key = "time_" + uid;
        Set<Long> set = new HashSet<>();
        set = zSetOperations.reverseRange(key, 0, System.currentTimeMillis());
        return set;
    }
}