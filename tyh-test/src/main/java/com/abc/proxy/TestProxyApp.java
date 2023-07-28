package com.abc.proxy;

import com.abc.annotation.aop.MyLogAnnotation;
import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: TestShopConfig
 * @Author: 青衣醉
 * @Date: 2022/7/15 2:36 下午
 */
public class TestProxyApp {
    public static void main(String[] args) {
        TestProxyImpl testProxy = new TestProxyImpl ();
        Class<? extends TestProxyImpl> aClass = testProxy.getClass ();
        Class<?>[] interfaces = aClass.getInterfaces ();
        TestProxy0 o = (TestProxy0)Proxy.newProxyInstance (
                // 目标资源对象类加载器
                aClass.getClassLoader (),
                // 获取目标对象的接口数组，应为java是单继承多实现
                interfaces,
                // 目标对象的调的方法
                new TargetInvoker (testProxy));
    }
    @Test
    @SneakyThrows
    public void jdkProxy(){
        TestProxy2 proxy =  (TestProxy2) JDKProxy.getProxy (new TestProxyImpl ());
        Method test01 = proxy.getClass ().getMethod ("test01");
        System.out.println (test01.getDeclaredAnnotation (MyLogAnnotation.class));
        System.out.println (proxy.test2 ("使用jdk动态代理"));
    }

    @Test
    public void jdkProxy2(){
        TestProxy0 proxy =  (TestProxy0) JDKProxy.getProxy (new TestProxyImpl ());
        System.out.println (proxy.test0 ());
        System.out.println (proxy.test01 ());
    }
    @Test
    public void cglibProxy(){
        CglibSevice proxy = (CglibSevice) CglibProxy.getProxy (CglibSevice.class);

        System.out.println (proxy.send ("测试cglib动态代理！"));
    }
}
