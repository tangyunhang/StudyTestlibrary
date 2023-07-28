package com.abc.client.controller;

import com.abc.client.configuration.TyhClientProperties;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: SocketApplicationRunner
 * @Author: 青衣醉
 * @Date: 2022/8/31 2:58 下午
 */
@Component
public class SocketApplicationRunner implements ApplicationRunner {

    @Value ("${tyh.socket-url}")
    private String socketUrl;
    @Value ("${spring.application.name}")
    private String serviceName;
    @Value ("${server.port}")
    private String port;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TyhClientProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
                    this.doLongPull ();
                },
                1000, 30000, TimeUnit.MILLISECONDS);
        // 1s 延迟后开始执行任务，每 2s 重复执行一次
    }

    public String doLongPull (){
        String url = socketUrl+"/listener";
        HashMap<String,Object> params = new HashMap<> ();
        HashMap<String,Object> headers = new HashMap<> (2);
        params.put ("serviceName",serviceName);
        headers.put("Long-Pulling-Timeout", "" + properties.getLongPullingTimeout ());
        headers.put("Client-AppName", "" + serviceName+port);
        HashMap hashMap = JSONObject.parseObject (properties.toString (), HashMap.class);
        headers.put("Client-Properties", "" + hashMap);
        // told server do not hang me up if new initializing cacheData added in
            headers.put("Long-Pulling-Timeout-No-Hangup", "true");
        restTemplate.postForObject (url, headers, HttpServletResponse.class);
        System.out.println ("---------logpull-----------");
        return "";
    }
}
