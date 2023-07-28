package com.abc.aop.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Description: MyAspect
 * @Author: 青衣醉
 * @Date: 2022/8/8 2:43 下午
 */

public class MyAspect {

    public void before(){
        System.out.println ("开启切面");
    }
    public void after(){
        System.out.println ("结束切面");
    }

}
