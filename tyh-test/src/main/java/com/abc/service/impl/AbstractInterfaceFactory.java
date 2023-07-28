package com.abc.service.impl;

import com.abc.bean.OrderInfo;
import com.abc.service.TestInterfaceService;

/**
 * @Description: AbstractInterfaceFactory
 * @Author: 青衣醉
 * @Date: 2022/12/12 10:06 上午
 */
public abstract class AbstractInterfaceFactory extends OrderInfo implements TestInterfaceService {

    public Object createInstance(){
        return new Object ();
    }
}
