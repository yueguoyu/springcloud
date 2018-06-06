package com.ygy.controller;

import com.ygy.dao.OssclientUtilDao;
import com.ygy.dao.TimelineDao;
import com.ygy.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author ygy
 * @date 2018/5/26
 */
@Controller
@RequestMapping("/user")
public class TimelineController {
    @Autowired
    TimelineDao timelineDao;
    @Autowired
    OssclientUtilDao clientUtil;

    /**
     * 增加动态
     *
     * @param timeline
     * @param file
     * @return
     */
    @RequestMapping(value = "/addTime", method = RequestMethod.POST)
    public String addTime(@RequestBody Timeline timeline, @RequestParam("file") MultipartFile file) {
        clientUtil.uplod(timeline.getUsername(), file);
        String url = file.getOriginalFilename();
        timeline.setFile(url);
        Date date = new Date(System.currentTimeMillis());
        timeline.setTime(date);
        timelineDao.add(timeline);
        return "main";
    }

    /**
     * 通过tid获取动态
     *
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public Timeline getTimelineByTid(@RequestParam("tid") long tid) {

        return timelineDao.selectById(tid);
    }

    /**
     * 获取用户的时间线
     *
     * @return
     */
    @RequestMapping("/getusertimels")
    public List<Timeline> getTimelinesByUid(@RequestParam("uid") long uid) {
        List<Timeline> times = timelineDao.selectByUserID(uid);
//        clientUtil.deleteBucket("ygy11");
        return times;
    }

    /**
     * 删除用户动态
     *
     * @return
     */
    @RequestMapping("/t1")
    public String te(@RequestParam("tid") long tid) {
        timelineDao.delete(tid);
        return "";
    }
}