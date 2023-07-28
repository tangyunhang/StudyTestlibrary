package com.abc.proxy;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: TargetInterceptor方法拦截接口
 * @Author: 青衣醉
 * @Date: 2022/7/26 5:07 下午
 */
public class TargetInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB 调用前，增强方法"+method.getName ());
        Object result = methodProxy.invokeSuper(obj, args);
        System.out.println("CGLIB 调用后");
        return  result;

    }
}
