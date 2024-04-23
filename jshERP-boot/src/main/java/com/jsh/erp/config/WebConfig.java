package com.jsh.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: YaoYuanming
 * @date: 2024/4/23 15:15
 * @description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 设置字符串的默认字符编码
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }
}