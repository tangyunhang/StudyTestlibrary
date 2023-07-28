package com.abc.proxy;

import com.abc.annotation.aop.MyLogAnnotation;

/**
 * @Description: TestProxyIpml
 * @Author: 青衣醉
 * @Date: 2022/7/21 4:52 下午
 */
public class TestProxyImpl implements TestProxy0,TestProxy1,TestProxy2{
    @Override
    public String test0() {
        System.out.println ("调用了test0方法");
        return "方法结束";
    }
    @Override
    @MyLogAnnotation
    public String test01() {
        System.out.println ("调用了test0方法");
        return "方法结束";
    }
    @Override
    public void test1(String mm) {
        System.out.println ("test1"+mm);

    }
    @Override
    public String test2(String cc) {
        System.out.println ("test2");

        return cc+"方法结束";
    }

}
