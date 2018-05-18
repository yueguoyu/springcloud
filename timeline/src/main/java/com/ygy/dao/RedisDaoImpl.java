package com.ygy.dao;


import com.ygy.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisDaoImpl implements RedisDao {
    @Autowired
    RedisTemplate redisTemplate;
    static HashOperations<Long, String, Object> useroperations;
    static ArrayList<Long> arrayList=new ArrayList<>();

    @Override
    public void addTimeline(long tid, Timeline timeline) {
        useroperations=redisTemplate.opsForHash();
        System.out.println("::::::"+tid+"   "+timeline.getText());
        useroperations.put(tid, "message", timeline.getText());
        useroperations.put(tid, "file", timeline.getFile());
        useroperations.put(tid, "posted", timeline.getTime());
        useroperations.put(tid, "uid", timeline.getUserid());
        useroperations.put(tid, "username", timeline.getUsername());
        useroperations.put(tid,"thumbsup",timeline.getThumbsup());
        arrayList.add(tid);
    }

    @Override
    public List<Map<String, Object>> getTimelines() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (arrayList!=null){
            for (Long tid : arrayList) {
                Map<String, Object> map = useroperations.entries(tid);
                mapList.add(map);
            }
        }else {
            return null;
        }
        return mapList;
    }
}