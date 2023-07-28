package com.abc.calculation.impl;

import com.abc.calculation.AbstractRuleTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: MoneyOffTemplate,满减
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:22 下午
 */
@Component
public class MoneyOffTemplate extends AbstractRuleTemplate {
    @Override
    protected Long calculateNewPrice(Long totalPrice, Long quota) {
        Long newtotalPrice =  totalPrice-quota;
        return newtotalPrice;
    }
}
