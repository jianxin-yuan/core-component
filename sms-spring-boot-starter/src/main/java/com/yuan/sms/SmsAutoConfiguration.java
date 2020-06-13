package com.yuan.sms;

import com.google.gson.Gson;
import com.yuan.sms.component.SmsComponent;
import com.yuan.sms.config.SmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SmsComponent.class)
    public SmsComponent smsComponent(SmsProperties smsProperties, Gson gson) {
        return new SmsComponent(smsProperties,gson);
    }
}
