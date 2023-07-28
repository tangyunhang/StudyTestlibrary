package com.abc.listener;

import com.abc.bean.OrderInfo;
import com.abc.event.CalculationApplicationEvent;
import com.abc.event.EmailApplicationEvent;
import com.abc.event.MessageEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description: EmailListener
 * @Author: 青衣醉
 * @Date: 2022/7/18 5:07 下午
 */
@Slf4j
@Component
public class MsgAngEmailListener {

    @SneakyThrows
    @EventListener
    public void sendEmail(EmailApplicationEvent applicationEvent){
        System.out.println ("test:测试注解监听");
        OrderInfo orderInfo = (OrderInfo) applicationEvent.getSource ();
        long start = System.currentTimeMillis();
        log.info("发送邮件");
        Thread.sleep(4000);
        long end = System.currentTimeMillis();
        log.info("{}：邮件发送耗时：({})毫秒", orderInfo.getOrderId (), (end - start));
    }

    @Async
    @SneakyThrows
    @EventListener
    public void sendMsg(MessageEvent applicationEvent){
        System.out.println ("test:测试注解监听");
        OrderInfo orderInfo = applicationEvent.getOrderInfo ();
        long start = System.currentTimeMillis();
        log.info("发送短信");
        Thread.sleep(4000);
        long end = System.currentTimeMillis();
        log.info("{}：短信发送耗时：({})毫秒", orderInfo.getOrderId (), (end - start));
    }
}
