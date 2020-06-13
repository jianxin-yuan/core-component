package com.yuan.ocr;

import com.yuan.ocr.component.OcrComponent;
import com.yuan.ocr.config.OcrProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OcrProperties.class)
public class OcrAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OcrProperties.class)
    public OcrComponent ocrComponent(OcrProperties ocrProperties) {

        return new OcrComponent(ocrProperties);
    }

}
