package com.yuan.oss.entity;

public class OssPolicy {

    private String accessKeyId;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private Long expire;
    private String bucket;

    public OssPolicy(String bucket, String accessId, String policy, String signature, String dir, String host,
                     Long expire) {
        this.bucket = bucket;
        this.accessKeyId = accessId;
        this.policy = policy;
        this.signature = signature;
        this.dir = dir;
        this.host = host;
        this.expire = expire / 1000;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
