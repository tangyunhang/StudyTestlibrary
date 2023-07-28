package com.abc.proxy;

/**
 * @Description: CglibSevice
 * @Author: 青衣醉
 * @Date: 2022/8/4 11:05 上午
 */
public class CglibSevice {

    public String send (String msg){
        System.out.println (msg);
        return "发送邮件成功";
    }
}
