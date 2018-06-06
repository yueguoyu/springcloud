package com.ygy.dao;

import com.ygy.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ygy.model.Comment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper mapper;
    static HashOperations<String, Long, HashMap<Long, String>> operations;

    private List<Long> list = new ArrayList<>();

    /**
     * 增加评论
     * @param comment
     * @param tid
     */
    @Override
    public void addComment(Comment comment, long tid) {
        operations = redisTemplate.opsForHash();
        String key = "Tcom_" + tid;
        HashMap<Long, String> map = new HashMap<>();
        map.put(comment.getPuserid(), comment.getText());
        operations.put(key, comment.getUserid(), map);
        list.add(tid);
        //将评论放到数据库中
        mapper.insert(comment);
    }

    /**
     * 获取指定动态的评论
     * @param tid
     * @return
     */
    @Override
    public ArrayList<Map<Long, HashMap<Long, String>>> getComment(long tid) {
        operations = redisTemplate.opsForHash();
        String key = "Tcom_" + tid;
        Map<Long, HashMap<Long, String>> map = operations.entries(key);
        ArrayList<Map<Long, HashMap<Long, String>>> list = new ArrayList<>();
        list.add(map);
//        mapper.selectById(cid);
        return list;
    }

    @Override
    public void deleteCo(int cid) {
    }

    /**
     * 0点触发数据上传到mysql数据库
     * 数据持久化
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void addToMysql() {
    }

}