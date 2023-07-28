package com.test2;

/**
 * @Description: Interface01
 * @Author: 青衣醉
 * @Date: 2023/4/17 4:36 下午
 */
public interface Interface02 {

    void test();
    default void test1(){
        System.out.println ("Interface02");
    }
}
