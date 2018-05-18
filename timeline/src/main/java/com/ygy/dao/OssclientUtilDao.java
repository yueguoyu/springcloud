package com.ygy.dao;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ygy
 * @date 2018/5/16
 * 阿里云oss对象存储工具
 */
public interface OssclientUtilDao {
    /**
     * @param key
     * @param file
     */
    void uplod(String key, MultipartFile file);

    /**
     * @param
     * @param
     * @return
     */
    String getUrl(String key);

    /**
     * @param bucketName
     */
    void creatBucket(String bucketName);

    /**
     * @param bucketName
     * @return
     */
    boolean deleteBucket(String bucketName);

    /**
     * @param key
     * @return
     */
    String getSignedUrl(String key);
}
