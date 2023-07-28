package com.abc.calculation.impl;

import com.abc.calculation.AbstractRuleTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: EmptyTemplate,无优惠
 * @Author: 青衣醉
 * @Date: 2022/7/18 2:21 下午
 */
@Component
public class EmptyTemplate extends AbstractRuleTemplate {
    @Override
    protected Long calculateNewPrice(Long totalPrice, Long quota) {
        return totalPrice;
    }
}
