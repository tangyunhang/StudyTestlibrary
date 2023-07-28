package com.abc.config;

import com.abc.interceptor.ShopInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Description: MyWebMvcConfguration
 * @Author: 青衣醉
 * @Date: 2022/8/4 4:25 下午
 */
@Configuration
public class MyWebMvcConfguration extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor (new ShopInterceptor ());
    }
}
