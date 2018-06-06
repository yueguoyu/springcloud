package com.ygy.model;

/**
 * @author ygy
 * @date 2018/6/6
 */
public class UserFollow {
    private long userid;
    private long followUid;
    private  long time;

    public void setFollowUid(long followUid) {
        this.followUid = followUid;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getFollowUid() {
        return followUid;
    }

    public long getTime() {
        return time;
    }

    public long getUserid() {
        return userid;
    }
}