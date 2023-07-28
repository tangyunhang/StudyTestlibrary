package com.abc.calculation.impl;

import com.abc.calculation.AbstractRuleTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: DiscountTemplate打折
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:23 下午
 */
@Component
public class DiscountTemplate extends AbstractRuleTemplate {
    @Override
    protected Long calculateNewPrice(Long totalPrice, Long quota) {
        return convertToDecimal (totalPrice*(quota.doubleValue ()/100));
    }
}
