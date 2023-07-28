package com.abc.proxy;

import org.springframework.cglib.proxy.Enhancer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Description: CglibProxy动态代理类
 * @Author: 青衣醉
 * @Date: 2022/7/27 10:15 上午
 */
public class CglibProxy {
    public static Object getProxy(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        // 设置类加载
      //  enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new TargetInterceptor());
        // 创建代理类
        Object o = enhancer.create ();
        //输出字节码文件
        extracted (enhancer);
        return o;
    }

    private static void extracted(Enhancer enhancer) {
        try {
            byte[] generate = new byte[0];
            generate = enhancer.getStrategy ().generate (enhancer);
            FileOutputStream fileOutputStream = new FileOutputStream (
                    new File ("/Users/tangyunhang/Studying/JAVAGJ/StudyTestLibrary/com/CglibProxy.class"));
            fileOutputStream.write (generate);
            fileOutputStream.flush ();
            fileOutputStream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
