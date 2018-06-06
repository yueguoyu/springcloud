package com.ygy.model;


import lombok.Data;

@Data
public class User {
    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 被关注的数量
     */
    private int followes;
    /**
     * 关注的数量
     */
    private int following;
    /**
     * 动态数
     */
    private int posts;
    /**
     * 点赞数
     */
    private long signup;
}