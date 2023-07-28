package com.abc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: TestCstomAopService测试自定义aop服务
 * @Author: 青衣醉
 * @Date: 2022/8/26 10:40 上午
 */
@Service
@Slf4j
public class TestCstomAopService {
    public void strengthenBefore(String name){
        System.out.println ("strengthenBefore="+name+Math());
    }
    public void strengthenAfter(String name){
        System.out.println ("strengthenAfter="+name+Math());
    }
    public void strengthenBeforewc(){
        System.out.println ("strengthenBefore=无掺");
    }
    public double Math(){
        return Math.random ();
    }
}
