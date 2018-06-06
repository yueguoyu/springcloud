package com.ygy.dao;

import com.ygy.mapper.UsertimelineMapper;
import com.ygy.model.Usertimeline;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ygy
 * @date 2018/5/21
 */
public class UserTimelineDaoImpl implements UserTimelineDao {
    @Autowired
    private UsertimelineMapper mapper;

    @Override
    public void addUserTimeline(Usertimeline usertimeline) {
        mapper.insert(usertimeline);
    }

    @Override
    public List<Long> selectUserTimeline(String userid) {
        return mapper.selectByUid(userid);
    }
}