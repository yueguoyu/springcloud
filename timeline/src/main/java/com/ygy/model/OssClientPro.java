package com.ygy.model;

public class OssClientPro {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}