package com.abc.aop.proxy;

import com.abc.annotation.aop.MyAfter;
import com.abc.annotation.aop.MyBefore;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Description: JDKAopProxy，支持MyBefore前置注解，与MyAfter后置注解
 * @Author: 青衣醉
 * @Date: 2022/8/9 4:45 下午
 */
@Slf4j
@NoArgsConstructor
public class JDKAopProxyBeanFactory {
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
        Object o = Proxy.newProxyInstance (aClass.getClassLoader (), aClass.getInterfaces (), new InvocationHandler () {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return invokeMethod(proxy, obj.getClass ().
                        getMethod (method.getName (),method.getParameterTypes ()), args);
            }
        });
        return o;
    }
    public Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent (MyBefore.class)) {
            log.info ("检测前置通知，执行前置加强");
            doBefore(proxy, method, args);
        }
        //执行主方法
        Object invoke = method.invoke (interfaceObj, args);

        if (method.isAnnotationPresent (MyAfter.class)) {
            log.info ("检测后置通知，执行后置加强");
            doAfter(proxy, method, args);
        }
        return invoke;
    }

    public  void doBefore(Object proxy, Method method, Object[] args) {
        MyBefore annotation = method.getAnnotation (MyBefore.class);
        if (null != annotation && annotation.value ().length >0) {
            Arrays.stream (annotation.value ())
                    .forEach (e->execute(e, method.getParameterTypes(), args));
        }
    }


    public void doAfter(Object proxy, Method method, Object[] args) {
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
