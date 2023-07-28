package com.abc.utils;

import com.abc.service.TestInterfaceService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: WebAppContextUtils
 * @Author: 青衣醉
 * @Date: 2022/12/2 10:55 上午
 */
@Component
public class WebAppContextUtils implements ApplicationContextAware {
    static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext ap) throws BeansException {
        applicationContext=ap;
        Map<String, TestInterfaceService> beansOfType = ap.getBeansOfType (TestInterfaceService.class);
        beansOfType.values ().forEach (System.out::println);
    }

    public static <T> Map<String,T> getServiceMap(Class<T> tClass){
        return applicationContext.getBeansOfType (tClass);
    }

}
