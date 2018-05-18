package com.ygy.dao;

import com.ygy.model.Timeline;

import java.util.List;
import java.util.Map;

public interface RedisDao {
    void addTimeline(long tid, Timeline timeline);
    List<Map<String,Object>> getTimelines();
}