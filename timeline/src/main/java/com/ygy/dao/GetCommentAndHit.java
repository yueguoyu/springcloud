package com.ygy.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author ygy
 * 获取commentAndHit服务
 * @date 2018/527
 */
@FeignClient(value = "comment")
public interface GetCommentAndHit {
    @RequestMapping(value = "/gethit", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    int getHit(@RequestParam(value = "tid") long tid);

    @RequestMapping(value = "/getcomment", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Map<Long, String>> getComment(@RequestParam(value = "tid") long tid);
}
