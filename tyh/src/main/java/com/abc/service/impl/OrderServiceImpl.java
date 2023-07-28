package com.abc.service.impl;

import com.abc.bean.OrderInfo;
import com.abc.event.CalculationApplicationEvent;
import com.abc.event.EmailApplicationEvent;
import com.abc.event.MessageEvent;
import com.abc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 * @Description: OrderServiceImpl
 * @Author: 青衣醉
 * @Date: 2022/7/18 11:13 上午
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ApplicationContext applicationContext;
    /**
     * @description: 
     * @author: 青衣醉
     * @date: 2022/7/19 9:29 上午
     **/
    @Override
    public String order(OrderInfo orderInfo) {
        long start = System.currentTimeMillis();
        //1、下单支付金额

        //2.计算积分（同步）
        applicationContext.publishEvent (new CalculationApplicationEvent (orderInfo));

        //3.邮件通知客户（异步）
        applicationContext.publishEvent (new EmailApplicationEvent (orderInfo));

        //4.短信通知客户（异步）
        applicationContext.publishEvent (new MessageEvent (orderInfo));

        long end = System.currentTimeMillis();
        log.info ("全部任务完成耗时：{}毫米",end-start);
        return "下单成功";
    }
}
