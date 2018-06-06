package com.ygy.dao;


import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * @author ygy
 * @date 2018/5/16
 */
@Component
public class OssclientUtilDaoImpl implements OssclientUtilDao {
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    String accessKeyId = "LTAIzgt7dyOScADr";
    String accessKeySecret = "Wd8HRZaMAIJ29vKBZ2YYFCPv0a5cW5";

    @Override
    public void uplod(String key, MultipartFile file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String fileName = file.getOriginalFilename();
        File newfile = new File(fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(newfile);
            outputStream.write(file.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ossClient.putObject("yueguoyu", key, newfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            newfile.delete();
            ossClient.shutdown();
        }
    }

    @Override
    public String getUrl(String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean found = false;
        URL url = null;
//        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        found = ossClient.doesObjectExist("yueguoyu", key);
        if (found) {
            SimplifiedObjectMeta metadata = ossClient.getSimplifiedObjectMeta("yueguoyu", key);
            metadata.getETag();
            url = ossClient.generatePresignedUrl("yueguoyu", key, expiration);
        } else {
            return "sorry";
        }
        ossClient.shutdown();
        return url.getFile();
    }

    @Override
    public void creatBucket(String bucketName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (bucketName == null) {

        }
        if (!ossClient.doesBucketExist(bucketName)) {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
        ossClient.shutdown();
    }

    @Override
    public boolean deleteBucket(String bucketName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean hasBucket = false;
        hasBucket = ossClient.doesBucketExist(bucketName);
        if (hasBucket) {
            ossClient.deleteBucket(bucketName);
        } else {
            return !hasBucket;
        }
        ossClient.shutdown();

        return hasBucket;
    }

    @Override
    public String getSignedUrl(String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String style = "image/resize,m_fixed,w_100,h_100/rotate,90";
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("yueguoyu", key, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL url = ossClient.generatePresignedUrl(req);
        ossClient.shutdown();
        return url.getFile();
    }

    @Override
    public String getSimpleUrl(String username, MultipartFile file) {
        String url = username + "/image/" + file.getOriginalFilename();
        return url;
    }

    @Override
    public void fileUplod(String filename, String str, String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        byte[] a = str.getBytes();
        File file = new File(filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(a);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ossClient.putObject("yueguoyu", key, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file.delete();
            ossClient.shutdown();
        }

    }
}
