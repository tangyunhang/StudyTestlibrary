package com.abc.aop.proxy;

import com.abc.annotation.aop.MyAfter;
import com.abc.annotation.aop.MyBefore;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * @Description: CglibAopProxyBeanFactory，支持MyBefore前置注解，与MyAfter后置注解
 * @Author: 青衣醉
 * @Date: 2022/8/9 4:45 下午
 */
@Slf4j
@NoArgsConstructor
public class CglibAopProxyBeanFactory {
    //被代理代理对象
    private   Object interfaceObj;

    /**
     * @description:
     * @param: 被代理类对象
     * @return: 返回代理对象
     **/
    public Object getProxyBean(Object obj){
        this.interfaceObj= obj;
        Class<?> aClass = obj.getClass ();
        Enhancer enhancer = new Enhancer();
        // 设置类加载
        enhancer.setClassLoader(aClass.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(aClass);
        // 设置方法拦截器
        enhancer.setCallback(new MethodInterceptor (){

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                return invokeMethod(obj,method,methodProxy, args);
               // return methodProxy.invokeSuper (obj, args);
            }
        });
        // 创建代理类
        Object o = enhancer.create ();
        return o;
    }
    public Object invokeMethod(Object obj,Method method, MethodProxy methodProxy, Object[] args) throws Throwable {
        if (method.isAnnotationPresent (MyBefore.class)) {
            log.info ("检测前置通知，执行前置加强");
            doBefore(method, args);
        }
        //执行主方法
       // Object invoke = methodProxy.invokeSuper (obj, args);
        Object invoke =methodProxy.invoke (interfaceObj, args);

        if (method.isAnnotationPresent (MyAfter.class)) {
            log.info ("检测后置通知，执行后置加强");
            doAfter( method, args);
        }
        return invoke;
    }

    public  void doBefore(Method method, Object[] args) {
        MyBefore annotation = method.getAnnotation (MyBefore.class);
        if (null != annotation && annotation.value ().length >0) {
            Arrays.stream (annotation.value ())
                    .forEach (e->execute(e, method.getParameterTypes(), args));
        }
    }


    public void doAfter(Method method, Object[] args) {
        MyAfter annotation = method.getAnnotation (MyAfter.class);
        if (null != annotation && annotation.value ().length >0) {
            Arrays.stream (annotation.value ())
                    .forEach (e->execute(e, method.getParameterTypes(), args));
        }
    }

    private void execute(String serviceName, Class<?>[] parameterTypes, Object[] args) {
        String[] nameArrays = splitServiceName(serviceName);
        //获取类名和方法名
        String className = nameArrays[0];
        String methodName = nameArrays[1];
        try {
            Object instance = Class.forName (className).getDeclaredConstructor ().newInstance ();
            Method method = instance.getClass ().getMethod (methodName,parameterTypes);
            method.invoke (instance,args);
        } catch (Exception e) {
            e.printStackTrace ();
            log.error ("serviceName:" + serviceName);
        }

    }

    private String[] splitServiceName(String serviceName) {
        int i = serviceName.lastIndexOf (".");
        if (i==-1){
            return null;
        }
        String[] nameArray = new String[2];
        nameArray[0] = serviceName.substring (0, i);
        nameArray[1] = serviceName.substring (i+1,serviceName.length ());
        return nameArray;
    }


}
