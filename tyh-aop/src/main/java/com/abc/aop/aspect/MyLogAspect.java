package com.abc.aop.aspect;

import com.abc.annotation.aop.MyLogAnnotation;
import com.abc.bean.SysLogEntity;
import com.abc.service.impl.MyLogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Description: MyLogAspect 日志切面
 * @Author: 青衣醉
 * @Date: 2022/8/8 4:27 下午
 */
@Aspect
@Component
@Slf4j
public class MyLogAspect {

    @Autowired
    MyLogService myLogService;
    //切点
    @Pointcut("@annotation(com.abc.annotation.aop.MyLogAnnotation)")
    public void pointoutLog(){

    }

    //前置执行
    @Before("pointoutLog()")
    public void doBeforeLog(JoinPoint joinPoint){
        log.info ("--doBeforeLog方法进入了--");
        //获得签名
        Signature signature = joinPoint.getSignature();
        //获取即将执行的方法名
        String method = signature.getName();
        log.info ("方法{},开始执行",method);
    }

    //前置执行
    @After ("pointoutLog()")
    public void doAfterLog(JoinPoint joinPoint){
        log.info ("--doAfterLog方法进入了--");
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        log.info ("方法{},结束执行",method);
    }
    @SneakyThrows
    @Around("pointoutLog()")
    public void doAround(ProceedingJoinPoint joinPoint){
        log.info ("===================开始环绕增强处理===================");

        long beginTime = System.currentTimeMillis();
        long time = System.currentTimeMillis() - beginTime;
        try {
            //实现保存日志逻辑
            saveLog(joinPoint, time);
        } catch (Exception e) {
            log.info (e.toString ());
        }
        joinPoint.proceed ();
        log.info ("===================结束环绕增强处理===================");
    }

    @SneakyThrows
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature ();
        Method method = signature.getMethod ();
       SysLogEntity sysLogEntity = new SysLogEntity ();
        sysLogEntity.setMethodName (method.getName ());
        sysLogEntity.setCreateDate (DateUtils.formatDate (new Date ()));
        sysLogEntity.setExeuTime (time);
        sysLogEntity.setRemark (method.getAnnotation (MyLogAnnotation.class).value ());
        sysLogEntity.setClassName (joinPoint.getTarget ().getClass ().getName ());
        sysLogEntity.setParams (
        Arrays.stream (joinPoint.getArgs ()).map (o->o.toString ())
                .collect (Collectors.toList ()).toString ());
        myLogService.saveLog (sysLogEntity);

    }
}
