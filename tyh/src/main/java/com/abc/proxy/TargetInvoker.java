package com.abc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: TargetInvoker
 * @Author: 青衣醉
 * @Date: 2022/7/21 5:14 下午
 */
public class TargetInvoker implements InvocationHandler {
    private Object target;
    public TargetInvoker(Object target) {
        this.target = target;
    }
    /**
     * @description:
     * @date: 2022/8/4 9:42 上午
     * @param: [proxy, method, args]
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk 代理执行前，增强"+method.getName ()+"方法");
        Object result = method.invoke(target, args);
        System.out.println("jdk 代理执行后");
        return result;
    }
}
