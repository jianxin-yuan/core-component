package com.yuan.sms.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author yuan
 * 短信发送返回值
 */
public class SmsResponse {
    /**
     * 状态码的描述
     */
    @SerializedName("Message")
    private String message;
    /**
     * 请求id
     */
    @SerializedName("RequestId")
    private String requestId;
    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态
     */
    @SerializedName("BizId")
    private String bizId;
    /**
     * 请求状态码
     */
    @SerializedName("Code")
    private String code;


    /**
     * 请求是否成功
     *
     * @return 成功返回true, 失败返回false
     */
    public boolean isSuccess() {
        return "OK".equals(this.code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
