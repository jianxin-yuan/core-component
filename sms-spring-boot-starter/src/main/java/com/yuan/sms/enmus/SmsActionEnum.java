package com.yuan.sms.enmus;

/**
 * 短信服务请求类型
 * @author yuan
 */
public enum SmsActionEnum {
    /**
     * 短信发送
     */
    SEND_SMS("SendSms"),
    /**
     * 查询短信发送结果
     */
    QUERY_SEND_DETAILS("QuerySendDetails");

    private String value;

    public String getValue() {
        return value;
    }

    SmsActionEnum(String value) {
        this.value = value;
    }
}
