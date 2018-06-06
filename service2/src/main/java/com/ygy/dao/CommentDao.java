package com.ygy.dao;

import com.ygy.model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ygy
 */
public interface CommentDao {
    void addComment(Comment comment, long tid);
    ArrayList< Map<Long,HashMap<Long,String>>> getComment(long tid);
    void deleteCo(int cid);
    void addToMysql();

}
