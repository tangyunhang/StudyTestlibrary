package com.abc.proxy;

import java.lang.reflect.Proxy;

/**
 * @Description: JDK动态代理类厂
 * @Author: 青衣醉
 * @Date: 2022/7/27 10:15 上午
 */
public class JDKProxy {
    public static Object getProxy(Object obj){
        Class<?> aClass = obj.getClass ();
        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象，我们来看下方法中的参数
         * 第一个参数： aClass.getClassLoader ()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：aClass.getInterfaces ()，这里为代理类提供的接口是真实对象实现的接口
         * ，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
        Object o = Proxy.newProxyInstance (aClass.getClassLoader (), aClass.getInterfaces (), new TargetInvoker (obj));
        return o;
    }
}
