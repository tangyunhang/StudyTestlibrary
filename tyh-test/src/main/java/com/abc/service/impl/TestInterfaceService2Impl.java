package com.abc.service.impl;

import com.abc.service.TestInterfaceService;
import org.springframework.stereotype.Service;

/**
 * @Description: TestInterfaceService1Impl
 * @Author: 青衣醉
 * @Date: 2022/12/2 11:05 上午
 */
@Service("testInterfaceService")
public class TestInterfaceService2Impl extends AbstractInterfaceFactory{
    @Override
    public void getRetult() {
        setOrderId ("fffff");
        System.out.println (getOrderId ());
    }

    @Override
    public String getType() {
            return "2";
    }
}
