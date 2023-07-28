package com.abc.listener;

import com.abc.bean.OrderInfo;
import com.abc.calculation.DisCountTemplateFactory;
import com.abc.calculation.RuleTemplate;
import com.abc.event.CalculationApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

/**
 * @Description: 监听下单事件，计算购物积分
 * @Author: 青衣醉
 * @Date: 2022/6/15 2:51 下午
 */
@Slf4j
@Component
public class CalculationApplicationListener implements ApplicationListener<CalculationApplicationEvent> {
    @Autowired
    DisCountTemplateFactory disCountTemplateFactory;
    @Override
    public void onApplicationEvent(CalculationApplicationEvent applicationEvent) {
        log.info (""+applicationEvent.getClass ()+":被监听了");
        long start = System.currentTimeMillis();
        log.info("开始计算积分");
        OrderInfo orderInfo = (OrderInfo)applicationEvent.getSource ();
        RuleTemplate template = disCountTemplateFactory.getTemplate (orderInfo);
        OrderInfo calculate = template.calculate (orderInfo);
        Long coat = calculateIntegral (calculate.getCost ());
        long end = System.currentTimeMillis();
        log.info ("本次消费所得积分：{}，计算耗时{}毫秒",coat,end);
    }

    public Long calculateIntegral(Long value) {
        BigDecimal divide = BigDecimal.valueOf (value).divide (new BigDecimal (2));
        return divide.setScale(0, RoundingMode.HALF_UP).longValue();
    }
}
