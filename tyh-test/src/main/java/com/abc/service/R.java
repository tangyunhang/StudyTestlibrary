package com.abc.service;

/**
 * @Description: R
 * @Author: 青衣醉
 * @Date: 2022/12/20 10:13 下午
 */
public class R {
    private Object msgs;
    private boolean success;
    public R(boolean success,Object msgs){
        this.success=success;
        this.msgs=msgs;
    }
}
