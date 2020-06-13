package com.yuan.ocr.config;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "ocr")
public class OcrProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;

    public OcrProperties() {
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
}
