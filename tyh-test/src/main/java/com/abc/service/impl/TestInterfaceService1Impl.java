package com.abc.service.impl;

import com.abc.service.TestInterfaceService;
import org.springframework.stereotype.Service;

/**
 * @Description: TestInterfaceService1Impl
 * @Author: 青衣醉
 * @Date: 2022/12/2 11:05 上午
 */
@Service("testInterfaceService1")
public class TestInterfaceService1Impl implements TestInterfaceService {
    @Override
    public void getRetult() {

    }

    @Override
    public String getType() {
        return "1";
    }

    public  void  test(){

    }
}
