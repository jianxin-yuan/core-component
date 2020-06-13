package com.yuan.sms.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sms服务配置类
 */
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    /**
     * AccessKey
     */
    private String accessKeyId;
    /**
     * AccessSecret
     */
    private String accessKeySecret;
    /**
     * 短信签名
     */
    private String signName;

    /**
     * 地域id,默认
     */
    private String regionId = "cn-hangzhou";
    /**
     * 域名
     */
    private String domain = "dysmsapi.aliyuncs.com";
    /**
     * 版本
     */
    private String version = "2017-05-25";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
