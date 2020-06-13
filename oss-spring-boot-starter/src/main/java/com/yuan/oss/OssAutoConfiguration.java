package com.yuan.oss;

import com.yuan.oss.component.OssComponent;
import com.yuan.oss.config.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OssComponent.class)
    public OssComponent ossComponent(OssProperties ossProperties) {
        return new OssComponent(ossProperties);
    }


}
