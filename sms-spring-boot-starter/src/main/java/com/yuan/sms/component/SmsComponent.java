package com.yuan.sms.component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.yuan.sms.config.SmsProperties;
import com.yuan.sms.enmus.SmsActionEnum;
import com.yuan.sms.entity.QueryParams;
import com.yuan.sms.response.QueryDetailResponse;
import com.yuan.sms.response.SmsResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author yuan
 * sms组件
 * 更多参考: https://api.aliyun.com/?spm=5176.12207334.0.0.79f81cbeJaleiW#/?product=Dysmsapi
 */
public class SmsComponent {
    private SmsProperties smsProperties;
    private Gson gson;
    private IAcsClient client;

    public SmsComponent(SmsProperties smsProperties, Gson gson) {
        this.smsProperties = smsProperties;
        this.gson = gson;
        client = new DefaultAcsClient(
                DefaultProfile.getProfile(
                        smsProperties.getRegionId(),
                        smsProperties.getAccessKeyId(),
                        smsProperties.getAccessKeySecret()
                )
        );
    }

    /**
     * 发送短信
     *
     * @param templateCode 短信模板code
     * @param phoneList    电话列表
     * @param params       json格式参数
     * @return 发送结果
     */
    public SmsResponse sendSms(String templateCode, List<String> phoneList, String params) {
        return doRequest(request -> {
            request.setSysAction(SmsActionEnum.SEND_SMS.getValue());
            request.putQueryParameter("TemplateCode", templateCode);
            request.putQueryParameter("PhoneNumbers", String.join(",", phoneList));
            request.putQueryParameter("TemplateParam", params);
        }, SmsResponse.class);
    }

    /**
     * 查询短信发送结果
     *
     * @param queryParams 查询参数
     * @return
     */
    public QueryDetailResponse querySmsRecord(QueryParams queryParams) {
        return doRequest(request -> {
            request.setSysAction(SmsActionEnum.QUERY_SEND_DETAILS.getValue());
            request.putQueryParameter("PhoneNumber", queryParams.getPhoneNumber());
            request.putQueryParameter("SendDate", queryParams.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            request.putQueryParameter("PageSize", queryParams.getPageSize() + "");
            request.putQueryParameter("CurrentPage", queryParams.getCurrentPage() + "");
            request.putQueryParameter("BizId", queryParams.getBizId());
        }, QueryDetailResponse.class);
    }

    /**
     * @param consumer request自定义填充字段函数
     * @param clazz    返回值class
     * @param <T>      返回值类型
     * @return
     */
    private <T> T doRequest(Consumer<CommonRequest> consumer, Class<T> clazz) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(smsProperties.getDomain());
        request.setSysVersion(smsProperties.getVersion());
        request.putQueryParameter("RegionId", smsProperties.getRegionId());
        request.putQueryParameter("SignName", smsProperties.getSignName());
        consumer.accept(request);
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            return gson.fromJson(commonResponse.getData(), clazz);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

}
