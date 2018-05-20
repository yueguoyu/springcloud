package com.ygy.controller;

import com.ygy.dao.OssclientUtilDao;
import com.ygy.dao.RedisDao;
import com.ygy.dao.TimelineDao;
import com.ygy.mapper.TimelineMapper;
import com.ygy.model.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class test {
    @Autowired
    TimelineDao timelineDao;
    @Autowired
    OssclientUtilDao clientUtil;

    @RequestMapping(value = "/addTime", method = RequestMethod.POST)
    public String addTime(@RequestParam("text") String text, @RequestParam("file") MultipartFile file) {
        Timeline timeline=new Timeline();
        String username = "ygy";
        String key = username + "/image/" + file.getOriginalFilename();
        clientUtil.uplod(key, file);
        String fileUrl = clientUtil.getUrl(key);
        timeline.setId((long)101);
        timeline.setUserid((long)123);
        timeline.setUsername("ygy");
        timeline.setThumbsup(1);
        timeline.setFile(fileUrl);
        timeline.setText(text);
        Date date=new Date(System.currentTimeMillis());
        timeline.setTime(date);
        timelineDao.add(timeline);
        redisDao.getSetTid(timeline.getUserid());
        return "main";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String headImgUpload(@RequestParam("file") MultipartFile file) {

        System.out.println(file.getOriginalFilename());
        clientUtil.uplod("image1/" + file.getOriginalFilename(), file);

        return "index";
    }

    @RequestMapping("/get")
    @ResponseBody
    public String test() {
        return "yueguoyu.oss-cn-beijing.aliyuncs.com" + clientUtil.getUrl("ygy/image/006.jpg");
    }

    @RequestMapping("/c")
    public String ce() {
        clientUtil.deleteBucket("ygy11");
        return "index";
    }
    @Autowired
    TimelineMapper mapper;
    @RequestMapping("/t1")
    public String te(){
        Timeline timeline1=new Timeline();
        timeline1.setText("dsa");
        timeline1.setFile("dsadsadsads");
        timeline1.setUserid((long)123);
        mapper.insert(timeline1);
        return "main";
    }

    @RequestMapping("/selectTime")
    @ResponseBody
    public Map selectTime(){
        Map map=new HashMap();
//       Timeline timeline= timelineDao.selectById((long)7);
        List<Timeline> list=timelineDao.selectByUserID(123);
        Timeline timeline= list.get(0);
        map.put(timeline.getId(),timeline.getFile());
       return map;
    }
    @RequestMapping(value = "/cs",method = RequestMethod.GET)
    @ResponseBody
    public  String cs(){
//      List<Timeline> list= timelineDao.selectByUserID(123);

//        return list.get(4).getFile();
      Timeline timeline= timelineDao.selectById(11);
        return timeline.getText();
    }
    @Autowired
    RedisDao redisDao;
    @RequestMapping(value = "/selectbytime",method = RequestMethod.GET)
    @ResponseBody
    public String s1(){
        redisDao.getSetTid(123);
        return "";

    }
    @RequestMapping("testy")
    public String string(){
       List<Timeline> list= mapper.selectByUserID(123);
       for (Timeline timeline:list){
           System.out.println(timeline.getId()+"    "+timeline.getTime().getTime());
           redisDao.addUserTimeline(123,timeline);
       }
        return "main";
    }

}